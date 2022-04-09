package com.legend.mybatis.dao;

import com.legend.mybatis.pojo.User;

import java.io.IOException;
import java.util.List;

/**
 * XXX
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/9
 */
public interface IUserDao {
    List<User> selectList() throws IOException;

    User selectOne(User user);
}
