package com.yzx.server.dao;

import com.yzx.server.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
  @Select("select * from user where account = #{account} and role = #{role}")
  User getUser(@Param("account") String account, @Param("role") Integer role);
}
