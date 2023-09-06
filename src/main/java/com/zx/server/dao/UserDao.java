package com.zx.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.server.domain.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {}

