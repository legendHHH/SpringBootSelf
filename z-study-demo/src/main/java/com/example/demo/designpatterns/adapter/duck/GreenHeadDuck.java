package com.example.demo.designpatterns.adapter.duck;

/**
 * 绿头鸭子
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/25
 */
public class GreenHeadDuck implements Duck{
    @Override
    public void quack() {
        System.out.println("Ga Ga Ga");
    }

    @Override
    public void fly() {
        System.out.println("I am flying long distance");
    }
}
