package com.example.demo.designpatterns.adapter;

import com.example.demo.designpatterns.adapter.duck.Duck;
import com.example.demo.designpatterns.adapter.duck.GreenHeadDuck;
import com.example.demo.designpatterns.adapter.turkey.WildTurkey;

/**
 * 主函数
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/25
 */
public class MainTest {

    public static void main(String[] args) {
        GreenHeadDuck duck = new GreenHeadDuck();
        duck.quack();
        duck.fly();

        WildTurkey turkey = new WildTurkey();
        turkey.gobble();
        turkey.fly();

        Duck duck2TurkeyAdapter = new TurkeyAdapter(turkey);

        //适配后
        duck2TurkeyAdapter.quack();
        duck2TurkeyAdapter.fly();


    }
}
