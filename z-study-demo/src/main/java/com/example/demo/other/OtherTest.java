package com.example.demo.other;

/**
 * 其他测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/29
 */
public class OtherTest {

    public static void main(String[] args) {
        Integer a = new Integer(5);
        Integer b = new Integer(5);

        Integer c = 5;
        Integer d = 5;

        Integer e = 128;
        Integer f = 128;
        int g = 128;

        //false
        System.out.println(a == b);

        //false
        System.out.println(b == c);

        //true
        System.out.println(c == d);

        //false
        System.out.println(e == f);

        //true
        System.out.println(f == g);

        System.out.println(System.identityHashCode(e));
        System.out.println(System.identityHashCode(f));
        System.out.println(System.identityHashCode(g));
    }
}
