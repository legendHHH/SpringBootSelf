package com.legend.springbootmybatis.controller;

import com.alibaba.fastjson.JSONObject;
import com.legend.springbootmybatis.dao.IPayment;
import com.legend.springbootmybatis.domain.Order;
import com.legend.springbootmybatis.domain.PayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    private static Set<String> needLoginRequest = new HashSet<String>() {{
        add("/homePage");
        add("/index3");
    }};

    @GetMapping("/test/index3")
    @ResponseBody
    public String method(HttpServletRequest request) {
        Boolean needLogin = false;
        for (String needLoginUrl : needLoginRequest) {
            if (request.getRequestURI().contains(needLoginUrl)) {
                needLogin = true;
            }
        }

        System.out.println(request.getRequestURI());
        return new StringBuilder(needLogin.toString()).append("-").append(request.getRequestURI()).toString();
    }

    /**
     * 测试当前服务
     * http://localhost:8083/imallapi/imall-shop/index2
     *
     * @return
     */
    @GetMapping("/index2")
    @ResponseBody
    public String method2() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", 200);
        map.put("msg", "进入内购商城成功");
        map.put("data", "{}");
        return JSONObject.toJSONString(map);
    }

}