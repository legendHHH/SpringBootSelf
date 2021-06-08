package com.example.demo.other;

import java.util.Arrays;
import java.util.List;

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

        int q1 = 10;
        int q2 = --q1;
        System.out.println("q2222=" + (q2--));
        int q3 = q2--;
        System.out.println("q1=" + q1);
        System.out.println("q2=" + q2);
        System.out.println("q3=" + q3);

        Integer aa = 1;
        List<Integer> list = Arrays.asList(1, 4, 5);
        System.out.println(list.contains(aa));

        System.out.println("字符串测试开始");
        //a:97   A:65
        char c1 = 'a';
        int num = 10;
        String str = "hello";
        //107hello
        System.out.println(c1 + num + str);
        //ahello10
        System.out.println(c1 + str + num);
        //a10hello
        System.out.println(c1 + (num + str));
        //107hello
        System.out.println((c1 + num) + str);
        //helloa10
        System.out.println(str + c1 + num);
    }
}
