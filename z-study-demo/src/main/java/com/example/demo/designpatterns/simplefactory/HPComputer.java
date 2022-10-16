package com.example.demo.designpatterns.simplefactory;

/**
 * 惠普电脑
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/10/16
 */
public class HPComputer extends Computer {

    @Override
    public void start() {
        System.out.println("惠普电脑启动");
    }
}
