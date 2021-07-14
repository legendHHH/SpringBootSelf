package com.example.demo.java8.interfaces.impl;

import com.legend.interfaces.NewCharacter;

/**
 * 新特性关键字--default
 * default声明的方法用来直接调用
 *
 * @author legend
 */
public class NewCharacterImpl implements NewCharacter {

    @Override
    public void test1() {
        System.out.println("test1");
    }

    /*@Override
    public void test2() {
        //System.out.println("test2");
    }*/

    public static void main(String[] args) {
        NewCharacter nca = new NewCharacterImpl();
        nca.test2();
    }
}
