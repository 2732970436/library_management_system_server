package com.yzx.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzx.server.domain.book.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookDao extends BaseMapper<Book> {
}
