package com.yzx.server.filter;

import com.yzx.server.exception.TokenAuthExpiredException;
import com.yzx.server.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    log.info(req.getRequestURI()+"=======进入拦截器========");
    // 如果不是映射到方法直接通过,可以访问资源.
    if (!(object instanceof HandlerMethod)) {
      System.out.println("not to function, pass");
      return true;
    }
    //为空就返回错误
    String token = req.getHeader("token");
    System.out.println(token);
    if (null == token || "".equals(token.trim())) {
      return false;
    }
    log.info("==============token:" + token);
    Map<String, String> map = tokenUtil.parseToken(token);
    String account = map.get("account");
    String role = map.get("role");
    long timeOfUse = System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"));
    //1.判断 token 是否过期
    //年轻 token
    if (timeOfUse < yangToken) {
      log.info("年轻 token");
    }
    //老年 token 就刷新 token
    else if (timeOfUse >= yangToken && timeOfUse < oldToken) {
      res.setHeader("token",tokenUtil.getToken(account,role));
    }
    //过期 token 就返回 token 无效.
    else {
      throw new TokenAuthExpiredException();
    }
    //2.角色匹配.
    if ("user".equals(role)) {
      return true;
    }
    return "admin".equals(role);
  }

}

