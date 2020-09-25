package com.qcl.permission.mapper;

import com.qcl.permission.model.SysUser;

/**
 * mapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/25
 */
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}