package com.legend.springbootmybatis.dao;

import com.legend.springbootmybatis.domain.Order;
import com.legend.springbootmybatis.domain.PayResult;

/**
 * 支付接口
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/18
 */
public interface IPayment {

    /**
     * 支付
     *
     * @param order
     * @return
     */
    PayResult pay(Order order);

}