package com.legend.mybatis.mapper;

import com.legend.mybatis.pojo.User;

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
     * 查询所有用户、同时查询每个用户关联的订单信息
     *
     * @return
     */
    public List<User> findAll();
}
