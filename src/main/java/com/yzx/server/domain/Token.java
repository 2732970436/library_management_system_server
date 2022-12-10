package com.yzx.server.domain;

import lombok.Data;

@Data
public class Token {
  public String token;

  public Token(String token) {
    this.token = token;
  }
}
