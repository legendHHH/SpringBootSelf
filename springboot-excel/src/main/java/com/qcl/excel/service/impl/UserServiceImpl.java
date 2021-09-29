package com.qcl.excel.service.impl;

import com.qcl.excel.domain.Users;
import com.qcl.excel.mapper.UserDaoMapper;
import com.qcl.excel.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserDaoMapper userDaoMapper;

    @Override
    public List<Users> getAllUser() {
        return userDaoMapper.getAllUser();
    }
}
