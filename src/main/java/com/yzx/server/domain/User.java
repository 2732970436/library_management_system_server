package com.yzx.server.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
  private Integer id;
  private String account;
  private String password;
  private String email;
  private Integer role;

  public User(Integer id, String account, String password, Integer role) {
    this.id = id;
    this.account = account;
    this.password = password;
    this.role = role;
  }

  public User(Integer id, String account, String password, String email, Integer role) {
    this.id = id;
    this.account = account;
    this.password = password;
    this.email = email;
    this.role = role;
  }


  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", account='" + account + '\'' +
        ", password='" + password + '\'' +
        ", email='" + email + '\'' +
        ", role=" + role +
        '}';
  }

  public User(String account, String password, Integer role) {
    this.account = account;
    this.password = password;
    this.role = role;
  }

}
