package com.legend.springbootmybatis.domain;

/**
 * 返回结果
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/18
 */
public class PayResult {

    /**
     * 支付结果
     */
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PayResult(String result) {
        this.result = result;
    }
}