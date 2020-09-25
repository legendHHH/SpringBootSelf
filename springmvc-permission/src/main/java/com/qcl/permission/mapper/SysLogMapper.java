package com.qcl.permission.mapper;

import com.qcl.permission.model.SysLog;
import com.qcl.permission.model.SysLogWithBLOBs;

/**
 * mapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/25
 */
public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLogWithBLOBs record);

    int insertSelective(SysLogWithBLOBs record);

    SysLogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    int updateByPrimaryKey(SysLog record);
}