package com.example.demo.java8.interfaces;

/**
 * 新特性关键字default
 *
 * @author legend
 */
public interface NewCharacter {
    public void test1();

    public default void test2() {
        System.out.println("legend我是新特性xxx");
    }
}
