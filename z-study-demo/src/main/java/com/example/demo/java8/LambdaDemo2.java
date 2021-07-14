package com.example.demo.java8;


import com.example.demo.java8.interfaces.MyInterface;

import java.util.function.Function;

/**
 * 内置的函数接口
 *
 * @author legend
 */
public class LambdaDemo2 {

    public static void main(String[] args) {
        //Lambda的写法
        System.out.println(toUpperString1(str -> str.toUpperCase(), "abc")); //ABC
        //匿名内部类的写法
        System.out.println(toUpperString1(new MyInterface<String>() {
            @Override
            public String getValue(String s) {
                return s;
            }
        }, "abc"));

        //使用内置的函数式接口的lambda写法
        System.out.println(toUpperString(str -> str.toUpperCase(), "abc"));
    }


    //内置的函数接口
    public static String toUpperString(Function<String, String> mn, String str) {
        return mn.apply(str);
    }

    //定义的函数接口
    public static String toUpperString1(MyInterface<String> mn, String str) {
        return mn.getValue(str);
    }
}
