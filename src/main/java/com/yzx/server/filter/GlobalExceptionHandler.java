package com.yzx.server.filter;

import com.yzx.server.controller.Result;
import com.yzx.server.exception.BusinessException;
import com.yzx.server.exception.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  /**
   * 用户 token 过期
   */
  @ExceptionHandler(value = BusinessException.class)
  public Result BusinessHandler(BusinessException ex){
    if (Objects.equals(ex.getCode(), Code.BUSINESS_TOKEN_ERROR)) {
      log.warn("用户 token 过期");
      return new Result(Code.BUSINESS_TOKEN_ERROR, "登录过期，请重新过期","Login expired, please expire again");
    }
    return new Result(Code.ERROR, ex.getMessage(),"Server is unusual, please refresh or login again");
  }
}
