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

    /**
     * 多条件组合查询：演示if
     *
     * @param user
     * @return
     */
    User findByCondition(User user);

    /**
     * 多值查询：演示foreach
     *
     * @param arr
     * @return
     */
    List<User> findByIds(int[] arr);
}
