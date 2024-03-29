package com.zx.server.exception;

public class SystemException extends RuntimeException {
  //自定义异常处理器，用于封装异常信息，对异常进行分类
  private Integer code;

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public SystemException(Integer code, String message) {
    super(message);
    this.code = code;
  }

  public SystemException(Integer code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }
}
