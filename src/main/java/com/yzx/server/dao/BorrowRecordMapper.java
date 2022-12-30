package com.yzx.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzx.server.domain.borrowRecord.BorrowRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BorrowRecordMapper extends BaseMapper<BorrowRecord> {
  @Select("SELECT br.*, a.book_name, ui.account as user_name from book_info as a JOIN borrow_record as br join user_info ui  ON ui.id = br.user_id and a.id = br.book_id where br.user_id = #{userId} and br.deleted = 0 and a.deleted = 0 and ui.deleted = 0")
  List<BorrowRecord> getAllRecords(@Param("userId") Long userId);

  List<BorrowRecord> getRecordsByCondition(@Param("userId") Integer userId, @Param("bookId") Integer bookId, @Param("start") Integer start, @Param("end") Integer end);

  Long getRecordsCount(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
}