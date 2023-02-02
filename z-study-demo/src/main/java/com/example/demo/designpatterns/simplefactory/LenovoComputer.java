package com.example.demo.designpatterns.simplefactory;

/**
 * 联想电脑
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/10/16
 */
public class LenovoComputer extends Computer {

    @Override
    public void start() {
        System.out.println("联想电脑启动");
    }
}
