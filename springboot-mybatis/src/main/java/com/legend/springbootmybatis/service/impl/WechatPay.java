package com.legend.springbootmybatis.service.impl;

import com.legend.springbootmybatis.dao.IPayment;
import com.legend.springbootmybatis.domain.Order;
import com.legend.springbootmybatis.domain.PayResult;
import org.springframework.stereotype.Service;

/**
 * 微信支付
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/18
 */
@Service("WechatPay")
public class WechatPay implements IPayment {

    @Override
    public PayResult pay(Order order) {
        return new PayResult("微信支付成功");
    }

}