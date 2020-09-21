package com.qcl.shiro.service;


import com.qcl.shiro.domain.User;

/**
 * UserService
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/21
 */
public interface UserService {

    User findByUsername(String username);
}
