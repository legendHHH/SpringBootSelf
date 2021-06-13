package com.example.demo.string;

/**
 * 字符串常量池
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/13
 */
public class MyString {

    public static void main(String[] args) {
        String str = new StringBuilder("58").append("hello").toString();
        System.out.println(str);
        System.out.println(str.intern());
        //首次遇到原则
        System.out.println(str == str.intern());

        //jdk在加载的时候会默认创建一个java的字符串(rt.jar包) ===>具体参考System类里面的源码的initializeSystemClass
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2);
        System.out.println(str2.intern());
        //很特殊的案例：false
        System.out.println(str2 == str2.intern());
        System.out.println("str2:" + str2.hashCode() + "\tstr2.intern:" + str2.intern().hashCode());

        String str3 = new StringBuilder("re").append("dis").toString();
        System.out.println(str3);
        System.out.println(str3.intern());
        System.out.println(str3 == str3.intern());

        String str4 = new StringBuilder("ng").append("inx").toString();
        System.out.println(str4);
        System.out.println(str4.intern());
        System.out.println(str4 == str4.intern());
    }
}
