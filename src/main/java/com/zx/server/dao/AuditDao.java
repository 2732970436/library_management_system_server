package com.zx.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.server.domain.audit.Audit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditDao extends BaseMapper<Audit> {
}
