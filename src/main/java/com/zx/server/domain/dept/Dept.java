package com.zx.server.domain.dept;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("dept")
public class Dept {
  @TableId
  private String dept;
  private Integer managerId;
  private String description;
  private Integer empNum;
  private String managerName;
}
