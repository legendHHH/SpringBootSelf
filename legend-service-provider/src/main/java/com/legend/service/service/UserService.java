package com.legend.service.service;

import com.legend.service.mapper.UserMapper;
import com.legend.service.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public User queryById(Long id){
        return this.userMapper.selectByPrimaryKey(id);
    }
}
