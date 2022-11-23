package com.example.demo.designpatterns.proxy.dynamicproxy;

/**
 * 普通实现类（委托方）
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/11/23
 */
public class RentingHouseImpl implements IRentingHouse {
    @Override
    public void rentHouse() {
        System.out.println("我要租用一房一厅的房子");
    }
}
