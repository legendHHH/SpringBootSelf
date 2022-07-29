package com.legend.mapper;

import com.legend.pojo.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * IRoleMapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/7/29
 */
public interface IRoleMapper {

    /**
     * 根据用户id查询角色信息 (配合com.legend.mapper.IOrderMapper#findOrderAndUserByAnnoation()方法查询数据)
     *
     * @param uid
     * @return
     */
    @Select("select * from sys_role r, sys_user_role ur where ur.roleid=r.id and userid=#{uid}")
    public List<Role> findRoleByUid(Integer uid);
}
