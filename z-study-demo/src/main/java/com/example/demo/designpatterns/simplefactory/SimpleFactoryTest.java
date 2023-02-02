package com.example.demo.designpatterns.simplefactory;

/**
 * 简单工厂测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/10/16
 */
public class SimpleFactoryTest {

    public static void main(String[] args) {
        Computer hp = ComputerFactory.createComputer("hp");
        hp.start();

    }
}
