package com.yzx.server.controller;

import com.yzx.server.exception.BusinessException;
import com.yzx.server.exception.Code;
import com.yzx.server.utils.CheckCodeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/library/api/checkCode")
public class CheckCodeImgController extends BaseController {
  @GetMapping
  public void getImg() {
    try (OutputStream imgout = res.getOutputStream();) {
      String verify_code = CheckCodeUtil.outputVerifyImage(100,32,imgout,3);
      session.setAttribute("code",verify_code);
    } catch (IOException e) {
      throw new BusinessException(Code.SYSTEM_CHECK_CODE_ERROR,"获取输入流错误",e);
    }
  }
}
