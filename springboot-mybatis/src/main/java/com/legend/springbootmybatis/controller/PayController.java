package com.legend.springbootmybatis.controller;

import com.legend.springbootmybatis.dao.IPayment;
import com.legend.springbootmybatis.domain.Order;
import com.legend.springbootmybatis.domain.PayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付服务
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/18
 */
@RestController
public class PayController {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 支付接口
     * http://localhost:8083//pay?amount=12&paymentType=UnionPay
     *
     * @param amount
     * @param paymentType
     * @return
     */
    @GetMapping("/pay")
    public PayResult pay(@RequestParam("amount") int amount,
                         @RequestParam("paymentType") String paymentType) {
        Order order = new Order();
        order.setAmount(amount);
        order.setPaymentType(paymentType);

        // 根据支付类型获取对应的策略 bean
        IPayment payment = applicationContext.getBean(order.getPaymentType(), IPayment.class);

        System.out.println("支付方式：" + payment.toString() + "    支付类型：" + paymentType);
        // 开始支付
        PayResult payResult = payment.pay(order);

        return payResult;
    }

}