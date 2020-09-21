package com.qcl.shiro.service.impl;

import com.qcl.shiro.domain.User;
import com.qcl.shiro.mapper.UserMapper;
import com.qcl.shiro.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * UserServiceImpl
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/21
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
