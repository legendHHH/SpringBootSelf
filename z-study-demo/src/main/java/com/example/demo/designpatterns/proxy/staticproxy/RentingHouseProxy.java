package com.example.demo.designpatterns.proxy.staticproxy;

/**
 * 静态代理类
 * 每一个业务对应一个代理类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/11/23
 */
public class RentingHouseProxy implements IRentingHouse {

    private IRentingHouse rentingHouse;

    public RentingHouseProxy(IRentingHouse rentingHouse) {
        this.rentingHouse = rentingHouse;
    }

    @Override
    public void rentHouse() {
        System.out.println("中介（代理）收取服务费3000元");
        rentingHouse.rentHouse();
        System.out.println("客户信息卖了3毛钱");
    }
}
