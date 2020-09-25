package com.qcl.permission.mapper;

import com.qcl.permission.model.SysRole;

/**
 * mapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/25
 */
public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}