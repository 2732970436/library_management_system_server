package com.zx.server.domain.salaryRecord;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("salary_records")
public class SalaryRecord {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @TableField("emp_name")
  private String empName;

  @TableField("total_wage")
  private Integer totalWage;

  @TableField("net_salary")
  private Integer netSalary;

  @TableField("salary_month")
  private String salaryMonth;
  @TableField("bonus")
  private Integer bonus;
}

