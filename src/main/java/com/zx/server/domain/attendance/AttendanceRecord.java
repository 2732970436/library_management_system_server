package com.zx.server.domain.attendance;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("attendancerecords")
public class AttendanceRecord {
  @TableId
  private Integer employeeId;
  private String employeeName;
  private String attendanceRecords;
}
