package com.yzx.server.domain.user;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_info")
public class User {
  @TableId(type = IdType.AUTO)
  private Long id;
  private Integer role;
  private String account;
  private String password;
  private String email;
  private String phone;
  private Integer status;
  @TableField(value="lastModifyTime",select=false)
  private Timestamp lastModifyTime;
  private Timestamp enrollTime;
  private Integer borrowCount;
  private Integer deleted;
}
