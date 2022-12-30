package com.yzx.server.controller;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.yzx.server.domain.user.User;
import com.yzx.server.exception.Code;
import com.yzx.server.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/user")
public class UserManageController extends BaseController{
  @Autowired
  private  UserService userService;

  /**
   * 用户模块由于业务的用户数量较少，直接采取调用全部用户，如需要可调用分页接口，前后端逻辑都已写好
   * @return
   */
  @GetMapping
  public Result getAllUsers_admin() {
    return new Result(Code.OK, userService.getAllUser(), "获取成功", "success");
  }

  @GetMapping("/{page}/{size}")
  public Result getUsersByPage_admin(@PathVariable Integer page, @PathVariable Integer size) {
     return new Result(Code.OK,userService.getUsersByPage(page, size), "查找成功", "The search was successful");
  }

  @PostMapping
  public Result addUsers_admin(@RequestBody List<User> users) {
     if(isAdmin() && userService.addUsers(users)) {
       return new Result(Code.OK, "添加成功","the addition was successful");
     }
     return new Result(Code.ERROR, "添加失败","the addition was failure");
  }

  @PatchMapping
  public Result updateUsers_admin(@RequestBody List<User> users) {
    if(userService.updateUsers(users)) {
      return new Result(Code.OK, "更新成功", "the modification was successful");
    }
      return new Result(Code.ERROR, "更新失败", "the modifycation was failure");
  }

  @DeleteMapping
  public Result deleteByIds_admin(@RequestBody List<Integer> ids) {
    if(userService.delUsers(ids)) {
      return new Result(Code.OK, "删除成功", "The deletion was successful");
    }
    return new Result(Code.ERROR, "删除失败", "The deletion was failure");
  }

  @GetMapping("/{account}")
  public Result getUsersByAccount_admin(@PathVariable String account) {
     return new Result(Code.OK, userService.getUsersByAccount(account), "获取成功", "success");
  }
}
