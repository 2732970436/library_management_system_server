package com.yzx.server.domain.book;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("book_info")
public class Book {
  @TableId(type = IdType.AUTO)
  private Long id;
  private String bookName;
  private Integer store;
  private String author;
  private Double price;
  private Integer remain;
  private Integer type;
  private Integer deleted;
}
