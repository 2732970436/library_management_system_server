package com.zx.server.controller;

import com.zx.server.dao.AttendanceDao;
import com.zx.server.domain.attendance.AttendanceRecord;
import com.zx.server.exception.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendanceRecords")
public class AttendanceController {
  @Autowired
  private AttendanceDao attendanceDao;

  @GetMapping("/{userId}")
  public Result getUserAttendance(@PathVariable Long userId) {
    return new Result(Code.OK, attendanceDao.selectById(userId), "", "");
  }

  @PostMapping
  public Result insertUserAttendance(@RequestBody AttendanceRecord attendanceRecord) {
    return new Result(Code.OK, attendanceDao.insert(attendanceRecord), "插入成功", "success");
  }

  }


