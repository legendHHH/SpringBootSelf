package com.legend.mybatis.mapper;

import com.legend.mybatis.pojo.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * IRoleMapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/10
 */
public interface IRoleMapper {

    @Select("select * from sys_role r, sys_user_role ur where ur.roleid=r.id and userid=#{uid}")
    public List<Role> findRoleByUid(Integer uid);
}
