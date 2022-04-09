package com.legend.mybatis.mapper;

import com.legend.mybatis.pojo.Order;

import java.util.List;

/**
 * IOrderMapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/9
 */
public interface IOrderMapper {

    /**
     * 查询订单的同时还查询该订单所属的用户
     *
     * @return
     */
    public List<Order> findOrderAndUser();
}
