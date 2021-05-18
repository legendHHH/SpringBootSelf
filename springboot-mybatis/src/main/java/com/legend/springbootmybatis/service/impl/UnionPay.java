package com.legend.springbootmybatis.service.impl;

import com.legend.springbootmybatis.dao.IPayment;
import com.legend.springbootmybatis.domain.Order;
import com.legend.springbootmybatis.domain.PayResult;
import org.springframework.stereotype.Service;

/**
 * 银联云闪付
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/18
 */
@Service("UnionPay")
public class UnionPay implements IPayment {

    @Override
    public PayResult pay(Order order) {
        return new PayResult("云闪付支付成功");
    }

}