package com.yzx.server.controller;

import com.yzx.server.domain.Token;
import com.yzx.server.domain.User;
import com.yzx.server.exception.Code;
import com.yzx.server.exception.SystemException;
import com.yzx.server.server.UserService;
import com.yzx.server.utils.TokenUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library/api/user")
public class UserController extends BaseController{
  private final UserService userService;
  private final TokenUtil tokenUtil;

  public UserController(UserService userService, TokenUtil tokenUtil) {
    this.userService = userService;
    this.tokenUtil = tokenUtil;
  }

  @GetMapping
  public Result login(String checkCode, @RequestBody  User requestUser) {
    System.out.println(requestUser);
    String account = requestUser.getAccount();
    String password = requestUser.getPassword();
    Integer role = requestUser.getRole();
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
      return new Result(Code.BUSINESS_LOGIN_TIME_ERROR, "null","错误次数达到上限，请在一分钟后再试");
    }

//    检查验证码
    if (/*checkCode(checkCode)*/true) {
      User user =  userService.getUser(account,role);
//      检查用户名和密码是否正确
       if (user != null && user.getPassword().equals(password)) {
         String token = tokenUtil.getToken(account,role.toString());
         res.setHeader("token", token);
         return new Result(Code.OK, new Token(token),"登录成功");
       }
      return new Result(Code.ERROR,null,"用户名或密码错误");
    }
      return new Result(Code.ERROR,null,"验证码错误");
  }

  @PostMapping
  public Result register(@RequestBody User requestuser) {
     
  }
}
