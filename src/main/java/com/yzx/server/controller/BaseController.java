package com.yzx.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {
// 这些对象何以直接被子类使用
  protected HttpServletRequest req;
  protected HttpServletResponse res;
  protected HttpSession session;

  @Value("${env}")
  protected String env;


  @ModelAttribute
  public void setReqAndRes(HttpServletRequest req, HttpServletResponse res) {
    this.req = req;
    this.res = res;
    this.session = req.getSession();
  }

  protected boolean checkCode(String code) {
    if (code == null) return false;
    return code.trim().toUpperCase().equals(session.getAttribute("code"));
  }

  protected boolean isAdmin() {
    int role = (int) session.getAttribute("role");
    return role == 1;
  }

}
