package com.qcl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcl.pojo.User;

import java.util.List;

/**
 * UserMapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/8/7
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询所有用户
     *
     * @return
     */
    public List<User> findAll();


    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    public int insert(User user);


}
