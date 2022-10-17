package com.example.demo.designpatterns.proxy;

/**
 * 创建名为Bob的人的实现类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/10/16
 */
public class Bob implements Person {

    @Override
    public void doSomething() {
        System.out.println("Bob do something");
    }
}
