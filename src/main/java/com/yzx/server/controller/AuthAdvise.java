package com.yzx.server.controller;

import com.yzx.server.exception.Code;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component

@Aspect
public class AuthAdvise{
//  拦截所有需要管理员权限才能进行的方法
  @Pointcut("execution(* com.yzx.server.controller.*.*_admin(..))")
  private void auth() {

  }

  /**
   * 对权限进行控制。先检测是否管理员身份，是允许操作，不是拒绝操作
   * @return Result
   */
  @Around("auth()")
  private Object authAround(ProceedingJoinPoint pjt) throws Throwable {
    HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    HttpServletResponse res=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    int role = (int) req.getSession().getAttribute("role");
    if (role == 1) {
      return pjt.proceed();
    }
    return new Result(Code.ERROR, null, "您没有权限", "You have no permit");
  }


}
