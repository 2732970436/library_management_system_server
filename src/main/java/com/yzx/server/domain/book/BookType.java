package com.yzx.server.domain.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookType {
  private Long id;
  private String name;
  private Integer deleted;
}
