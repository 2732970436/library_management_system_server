package com.yzx.server.controller;

import com.yzx.server.exception.Code;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result {
  public Integer code;
  public Object data;
  public String message;
  public String messageE;

  public Result(Integer code, String message, String messageE) {
    this.code = code;
    this.message = message;
    this.messageE = messageE;
  }

  public Result(Integer code, Object data, String message, String messageE) {
    this.code = code;
    this.data = data;
    this.message = message;
    this.messageE = messageE;
  }


}