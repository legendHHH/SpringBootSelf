package com.legend.mybatis.mapper;

import com.legend.mybatis.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * IUserMapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/9
 */
public interface IUserMapper {

    /**
     * 查询所有用户
     *
     * @return
     */
    public List<User> findAll();

    /**
     * 多条件组合查询：演示if
     *
     * @param user
     * @return
     */
    public List<User> findByCondition(User user);


    /**
     * 多值查询：演示foreach
     *
     * @param ids
     * @return
     */
    public List<User> findByIds(int[] ids);
}
