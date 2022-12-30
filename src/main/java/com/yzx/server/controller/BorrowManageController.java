package com.yzx.server.controller;

import com.yzx.server.domain.borrowRecord.BorrowRecord;
import com.yzx.server.exception.Code;
import com.yzx.server.server.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/library/borrows")
public class BorrowManageController {
  @Autowired
  BorrowService borrowRecordService;

  @GetMapping("/{userId}/{bookId}/{page}/{size}")
  public Result getBorrowInfoByCondition_admin(@PathVariable Integer userId, @PathVariable Integer bookId, @PathVariable Integer page, @PathVariable Integer size) {
    return new Result(Code.OK,borrowRecordService.getBorrowInfoByConditionAndPage(userId, bookId, page * (size - 1), page * size) , "获取成功", "success");
  }

  @PutMapping
  public Result approval_admin(@RequestBody BorrowRecord borrowRecord) {
      return new Result(Code.OK, borrowRecordService.changeApproval(borrowRecord), "修改成功", "success");
  }
}
