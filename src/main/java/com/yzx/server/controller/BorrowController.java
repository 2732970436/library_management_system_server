package com.yzx.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.server.domain.MyPage;
import com.yzx.server.exception.Code;
import com.yzx.server.server.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/library/borrow")
public class BorrowController extends BaseController{
  @Autowired
  BorrowService borrowRecordService;

  @PostMapping("/{bookId}")
  public Result borrow(@PathVariable Long bookId) {
    return borrowRecordService.borrowBook(bookId,(Long)session.getAttribute("userId"));
  }

  @GetMapping("/{userId}")
  public Result getBorrowInfo(@PathVariable Integer userId) {
    if (userId != session.getAttribute("userId")) {
      return new Result(Code.ERROR, "错误的用户参数，请先重新登录", "Wrong user preferences, please login again");
    }
    return new Result(Code.OK, borrowRecordService.getBorrowInfo((Long) session.getAttribute("userId")), "查询成功", "success");
  }



  @PatchMapping("/{id}")
  public Result returnBook(@PathVariable Long id) {
    return borrowRecordService.returnBook(id);
  }

}
