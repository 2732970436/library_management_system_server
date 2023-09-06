package com.zx.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.server.domain.dept.Dept;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeptDao extends BaseMapper<Dept> {
}
