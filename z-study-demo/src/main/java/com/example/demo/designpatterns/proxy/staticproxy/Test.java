package com.example.demo.designpatterns.proxy.staticproxy;

/**
 * XXX
 *
 * @author qichunlin@grgbanking.com
 * @version 1.0
 * @description
 * @date 2022/11/23
 */
public class Test {
    public static void main(String[] args) {
        IRentingHouse rentingHouse = new RentingHouseImpl();
        //自己要租用一个一室一厅的房子
        rentingHouse.rentHouse();

        //代理类
        RentingHouseProxy proxy  = new RentingHouseProxy(rentingHouse);
        proxy.rentHouse();
    }
}
