package com.zx.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zx.server.dao.DeptDao;
import com.zx.server.domain.dept.Dept;
import com.zx.server.exception.Code;
import com.zx.server.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {
  @Autowired
  private DeptDao deptDao;
  @Autowired
  private UserService userService;

  @GetMapping
  public Result getDept() {
    List<Dept> depts = deptDao.selectList(null);
    for(Dept dept : depts) {
      dept.setEmpNum(userService.getUsersByDepartment(dept.getDept()).size());
    }
    return new Result(200, depts, "获取成功", "success");
  }

  @GetMapping("/{deptName}")
  public Result getUserDept(@PathVariable String deptName) {
    Dept dept = deptDao.selectOne(new QueryWrapper<Dept>().eq("dept",deptName));
    dept.setEmpNum(userService.getUsersByDepartment(dept.getDept()).size());
    return new Result(Code.OK, dept, "获取成功", "success");
  }

  @PatchMapping
  public Result updateDept(@RequestBody Dept dept) {
    return new Result(Code.OK, deptDao.updateById(dept), "更新成功", "success");
  }

  @PostMapping
  public Result insertDept(@RequestBody Dept dept) {
    return new Result(Code.OK, deptDao.insert(dept), "插入成功", "success");
  }

  @DeleteMapping
  public Result deleteDept(@RequestBody Dept dept) {
    return new Result(Code.OK, deptDao.deleteById(dept.getDept()), "删除成功", "success");
  }


  
}
