package com.yzx.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzx.server.domain.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {}

