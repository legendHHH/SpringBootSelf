package com.legend.spring.bean;

import org.springframework.stereotype.Component;

/**
 * 生命周期
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
@Component
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
