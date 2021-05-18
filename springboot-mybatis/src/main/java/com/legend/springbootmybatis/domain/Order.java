package com.legend.springbootmybatis.domain;

/**
 * 订单信息
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/18
 */
public class Order {

    /**
     * 金额
     */
    private int amount;

    /**
     * 支付类型
     */
    private String paymentType;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}