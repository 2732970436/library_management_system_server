package com.zx.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zx.server.domain.salaryRecord.SalaryRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SalaryRecordDao extends BaseMapper<SalaryRecord> {
}
