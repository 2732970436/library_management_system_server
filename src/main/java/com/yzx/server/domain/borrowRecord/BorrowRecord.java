package com.yzx.server.domain.borrowRecord;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecord {
  @TableId(type=IdType.AUTO)
  private Long id;
  private Long bookId;
  @TableField(exist = false)
  private String bookName;
  private Long adminId;
  private Long userId;
  @TableField(exist = false)
  private String userName;
  private Timestamp startTime;
  private Long returnStatus;
  private Long borrowDays;
  private Long deleted;
  private Timestamp returnTime;
  /**
   * 审批进度，0为待审批，1为审批通过，2为审批未被批准
   */
  private Long approvalStatus;

  public BorrowRecord(Long bookId, Long adminId, Long userId, Timestamp startTime, Long returnStatus, Long borrowDays, Long deleted, Timestamp returnTime, Long approvalStatus) {
    this.bookId = bookId;
    this.adminId = adminId;
    this.userId = userId;
    this.startTime = startTime;
    this.returnStatus = returnStatus;
    this.borrowDays = borrowDays;
    this.deleted = deleted;
    this.returnTime = returnTime;
    this.approvalStatus = approvalStatus;
  }

  /**
   * 用于新建记录,默认借阅时间为60天
   * @param bookId 书本id
   * @param userId 用户id
   */
  public BorrowRecord(Long bookId, Long userId) {
    this.returnStatus = 0L;
    this.borrowDays = 60L;
    this.approvalStatus = 0L;
    this.bookId = bookId;
    this.userId = userId;
  }
}
