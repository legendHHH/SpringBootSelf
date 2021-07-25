package com.example.demo.designpatterns.adapter;

import com.example.demo.designpatterns.adapter.duck.Duck;
import com.example.demo.designpatterns.adapter.turkey.Turkey;

/**
 * 鸭子适配器类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/25
 */
public class TurkeyAdapter implements Duck {

    private Turkey turkey;

    public TurkeyAdapter(Turkey turkey) {
        this.turkey = turkey;
    }

    @Override
    public void quack() {
        turkey.gobble();
    }

    @Override
    public void fly() {
        for (int i = 0; i < 6; i++) {
            turkey.fly();
        }
    }
}
