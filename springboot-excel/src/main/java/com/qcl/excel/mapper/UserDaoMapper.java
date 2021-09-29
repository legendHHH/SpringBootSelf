package com.qcl.excel.mapper;

import com.qcl.excel.domain.Users;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDaoMapper {
    //查询所有用户
    @Select("select id,username,password from users")
    List<Users> getAllUser();
}
