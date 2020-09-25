package com.qcl.permission.mapper;

import com.qcl.permission.model.SysDept;

/**
 * mapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/25
 */
public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);
}