package com.legend.spring.bean;

/**
 * 生命周期
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
public class Car {

    public Car() {
        System.out.println("Car Constructor.......");
    }

    public void initMethod(){
        System.out.println("Car initMethod.......");
    }

    public void destroyMethod(){
        System.out.println("Car destroyMethod.......");
    }
}
