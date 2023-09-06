package com.zx.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zx.server.dao.AuditDao;
import com.zx.server.domain.audit.Audit;
import com.zx.server.exception.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salary/audit")
public class AuditController {
  @Autowired
  private AuditDao auditDao;

  @GetMapping
  public Result getAudit() {
    return new Result(200, auditDao.selectList(null), "获取成功", "success");
  }

  @GetMapping("/{userId}")
  public Result getUserAudit(@PathVariable String userId) {
    return new Result(Code.OK, auditDao.selectList(new QueryWrapper<Audit>().eq("emp_id",userId)), "获取成功", "success");
  }
  @PatchMapping
  public Result updateAudit(@RequestBody Audit audit) {
    return new Result(Code.OK, auditDao.updateById(audit), "更新成功", "success");
  }

  @PostMapping
    public Result insertAudit(@RequestBody Audit audit) {
        return new Result(Code.OK, auditDao.insert(audit), "插入成功", "success");
    }
}
