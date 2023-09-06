package com.zx.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zx.server.dao.SalaryRecordDao;
import com.zx.server.domain.salaryRecord.SalaryRecord;
import com.zx.server.exception.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salaryRecord/salaryRecord")
public class SalaryRecordController {
  @Autowired
  private SalaryRecordDao salaryRecordDao;

  @GetMapping
  public Result getSalarys() {
    return new Result(200, salaryRecordDao.selectList(null), "获取成功", "success");
  }

  @GetMapping("/{empName}")
  public Result getUserAudit(@PathVariable String empName) {
    return new Result(Code.OK, salaryRecordDao.selectList(new QueryWrapper<SalaryRecord>().eq("emp_name",empName)), "获取成功", "success");
  }
  @PatchMapping
  public Result updateAudit(@RequestBody SalaryRecord salaryRecord) {
    return new Result(Code.OK, salaryRecordDao.updateById(salaryRecord), "更新成功", "success");
  }

  @PostMapping
  public Result insertAudit(@RequestBody SalaryRecord salaryRecord) {
    return new Result(Code.OK, salaryRecordDao.insert(salaryRecord), "插入成功", "success");
  }

  @DeleteMapping
    public Result deleteAudit(@RequestBody SalaryRecord salaryRecord) {
        return new Result(Code.OK, salaryRecordDao.deleteById(salaryRecord.getId()), "删除成功", "success");
    }
}
