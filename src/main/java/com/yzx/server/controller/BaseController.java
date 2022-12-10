package com.yzx.server.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class BaseController {
// 这些对象何以直接被子类使用
  protected HttpServletRequest req;
  protected HttpServletResponse res;
  protected HttpSession session;


  @ModelAttribute
  public void setReqAndRes(HttpServletRequest req, HttpServletResponse res) {
    this.req = req;
    this.res = res;
    this.session = req.getSession();
  }

  public boolean checkCode(String code) {
    if (code == null) return false;
    return code.trim().toUpperCase().equals(session.getAttribute("code"));
  }
}
