package com.example.demo.designpatterns.adapter.turkey;

/**
 * 鸭子类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/25
 */
public class WildTurkey implements Turkey{
    @Override
    public void gobble() {
        System.out.println("Go Go Go");
    }

    @Override
    public void fly() {
        System.out.println("I am flying short distance");
    }
}
