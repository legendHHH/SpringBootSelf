package com.qcl.excel.service;

import com.qcl.excel.domain.Users;

import java.util.List;

public interface UserService {
    //查询所有用户
    List<Users> getAllUser();
}