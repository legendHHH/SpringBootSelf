package com.qcl.shiro.mapper;

import com.qcl.shiro.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * mapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/21
 */
public interface UserMapper {

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    User findByUsername(@Param("username") String username);
}
