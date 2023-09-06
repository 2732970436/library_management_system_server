package com.zx.server.domain.audit;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("audit")
public class Audit {
  @TableId(value = "audit_id", type = IdType.AUTO)
  private Long auditId;

  @TableField("emp_id")
  private Long empId;

  @TableField("audit_content")
  private String auditContent;

  @TableField("audit_status")
  private Integer auditStatus;

  @TableField("audit_processer")
  private Long auditProcesser;

  @TableField("process_content")
  private String processContent;

  @TableField(value = "audit_date", fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime auditDate;

  @TableField(value = "process_date", fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime processDate;
  @TableField(value = "emp_name")
  private String empName;
  @TableField(value = "processer_name")
  private String processerName;
}
