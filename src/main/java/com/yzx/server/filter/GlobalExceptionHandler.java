package com.yzx.server.filter;

import com.yzx.server.controller.Result;
import com.yzx.server.exception.Code;
import com.yzx.server.exception.TokenAuthExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
  /**
   * 用户 token 过期
   * @return
   */
  @ExceptionHandler(value = TokenAuthExpiredException.class)
  public Result tokenExpiredExceptionHandler(){
    log.warn("用户 token 过期");
    return new Result(Code.BUSINESS_TOKEN_ERROR,null,"登录过期，请重新过期");
  }
}
