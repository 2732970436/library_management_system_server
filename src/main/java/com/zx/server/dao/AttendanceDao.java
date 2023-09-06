package com.zx.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.server.domain.attendance.AttendanceRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttendanceDao extends BaseMapper<AttendanceRecord> {
}
