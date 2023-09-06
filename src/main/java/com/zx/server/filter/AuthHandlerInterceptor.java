package com.zx.server.filter;

import com.zx.server.exception.BusinessException;
import com.zx.server.exception.Code;
import com.zx.server.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
  @Autowired
  TokenUtil tokenUtil;
  @Value("${token.privateKey}")
  private String privateKey;
  @Value("${token.yangToken}")
  private Long yangToken;
  @Value("${token.oldToken}")
  private Long oldToken;

  /**
   * 权限认证的拦截操作.
   */
  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object object) throws Exception {
    log.info(req.getRequestURI() + "=======进入拦截器========");
    // 如果不是映射到方法直接通过,可以访问资源.
    if (!(object instanceof HandlerMethod)) {
      return true;
    }
    //为空就返回错误
    String token = req.getHeader("token");
    if (null == token || "".equals(token.trim())) {
      log.info("path: " + req.getContextPath()  + ";   ip:  " + req.getRemoteAddr() + ":" + req.getRemoteHost() +  "'s token is invalid;");
      throw new BusinessException(Code.BUSINESS_TOKEN_ERROR, "token无效");
    }
    Map<String, String> map = tokenUtil.parseToken(token);
    String account = map.get("account");
    String role = map.get("role");
    String id = map.get("id");
    log.info(account + "(" + role + ") is login");
    long timeOfUse = System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"));
    //1.判断 token 是否过期
    //年轻 token
    if (timeOfUse < yangToken) {
      log.info("年轻 token");
    }
    //老年 token 就刷新 token
    else if (timeOfUse >= yangToken && timeOfUse < oldToken) {
      res.setHeader("token", tokenUtil.getToken(account, role,Long.parseLong(id)));
    }
    //过期 token 就返回 token 无效.
    else {
      throw new BusinessException(Code.BUSINESS_TOKEN_ERROR, "请重新登录");
    }

    HttpSession session =  req.getSession();
    session.setAttribute("role", "0".equals(role) ? 0 : 1 );
    session.setAttribute("account", account);
    session.setAttribute("userId",Long.parseLong(id));
    //2.角色匹配.
//    String url = req.getRequestURI();
//    String pattern = "^/library/book*";
//    String method = req.getMethod();
//    if (Pattern.matches(pattern, url)) {
//      if (Objects.equals(role, "0")) {
//        return
//      }
//    }

    return true;
  }
}

