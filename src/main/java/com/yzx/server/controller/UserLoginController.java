package com.yzx.server.controller;

import com.yzx.server.domain.user.User;
import com.yzx.server.exception.BusinessException;
import com.yzx.server.exception.Code;
import com.yzx.server.exception.SystemException;
import com.yzx.server.server.UserService;
import com.yzx.server.utils.TokenUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/library/api/profile")
public class UserLoginController extends BaseController{
  private final UserService userService;
  private final TokenUtil tokenUtil;


  public UserLoginController(UserService userService, TokenUtil tokenUtil) {
    this.userService = userService;
    this.tokenUtil = tokenUtil;
  }

  /**
   * 登录接口，前端请求此接口请求登录，成功就返回token,并将用户相关信息记录到session中
   * @param checkCode  验证码
   * @param account  账户名
   * @param password  密码
   * @param role  用户账户类型
   * @return  Result
   */
  @GetMapping
  public Result login(String checkCode, String account, String password, Integer role) {
//    记录登录次数
   Integer loginTime = (Integer) session.getAttribute("loginTime");
   if (loginTime == null) {
     session.setAttribute("loginTime",1);
   } else {
     session.setAttribute("loginTime",loginTime+1);
   }
//    如果错误次数达到三次则禁止登录
    if ((Integer)session.getAttribute("loginTime") > 3) {
//      延迟一分钟后刷新session中的登录次数
      new Thread(() -> {
        try {
          Thread.sleep(1000*60);
          session.setAttribute("loginTime",0);
        } catch (InterruptedException e) {
          throw new SystemException(Code.ERROR,"定时器线程异常",e);
        }
      }).start();
      return new Result(Code.BUSINESS_LOGIN_TIME_ERROR, "null","错误次数达到上限，请在一分钟后再试", "The number of errors reaches the limit, please try again in one minute");
    }

//    检查验证码
    if (checkCode(checkCode) || env.equals("development")) {
      User user =  userService.getUser(account,role);
//      检查用户名和密码是否正确
       if (user != null && user.getPassword().equals(password)) {
         String token = tokenUtil.getToken(account,role.toString(), user.getId());
         res.setHeader("token", token);
         session.setAttribute("loginTime",0);
         Map<String, Object> map = new HashMap<>();
         map.put("user", user);
         map.put("token", token);
         return new Result(Code.OK, map,"登录成功", "login success");
       }
      return new Result(Code.ERROR,null,"用户名或密码错误", "The username or password is incorrect");
    }
      return new Result(Code.ERROR,null,"验证码错误", "Captcha error");
  }

  /**
   * 检查
   * @return 账户是否存在
   */
  @GetMapping("/{account}/{role}")
  public Result checkAccount(@PathVariable String account, @PathVariable Integer role) {
    if(userService.checkAccountIsExist(account, role)) {
      return new Result(Code.OK, "账户已存在", "Account already exists");
    }
    return new Result(Code.ERROR, "账户不存在","Account does not exist");
  }

  @PostMapping
  public Result register(@RequestBody User user) {
    if (userService.enroll(user)) {
      res.setHeader("token",tokenUtil.getToken(user.getAccount(), user.getRole().toString(), user.getId()));
      return new Result(Code.OK, "注册成功", "Registration successful");
    }
    return new Result(Code.ERROR, "注册失败", "Registration failure");
  }

  /**
   * 用户头像获取
   */
  @GetMapping("/avatar/img/{userId}")
  public Result getAvator(@PathVariable Long userId) {
    try {
      OutputStream os = res.getOutputStream();
      File avatar = new File("avatar" + File.separator + userId);
      if (!avatar.exists()) {
        avatar = new File("avatar" + File.separator + "default");
      }
      InputStream fs = Files.newInputStream(avatar.toPath());
      IOUtils.copy(fs, os);
      return new Result(Code.OK, "获取成功", "success");
    } catch (IOException e) {
      throw new BusinessException(Code.AVATAR_ERROR, "用户头像获取错误");
    }
  }

  @PostMapping("/avatar/img/{userId}")
  public Result uploadAvatar(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
    try {

      InputStream is = file.getInputStream();
      File avatar = new File("avatar" + File.separator + userId);
      OutputStream os = Files.newOutputStream(avatar.toPath());
      IOUtils.copy(is,os);
      os.close();
      return new Result(Code.OK, "设置成功", "success");
    } catch (IOException e) {
      throw new BusinessException(Code.AVATAR_ERROR, "头像设置错误");
    }

  }

  @PatchMapping
  public Result updateProfile(@RequestBody User user) {
    if (session.getAttribute("userId") != user.getId().toString()) {
      return new Result(Code.ERROR, "非法的修改,您还未登录或尝试以非管理员身份修改其他用户", "Illegal modification, you are not logged in or try to the administrator to modify other users");
    }
    if (!userService.updateUser(user)) {
      return new Result(Code.ERROR, "插入数据时错误", "some error occur on insert data");
    }
    return new Result(Code.OK, "修改成功", "success");
  }

}
