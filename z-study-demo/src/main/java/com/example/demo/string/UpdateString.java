package com.example.demo.string;

import java.lang.reflect.Field;

/**
 * 如何在不修改String的引用地址下，将String s= "abc" 改为  "abcd"
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/8/18
 */
public class UpdateString {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String s = new String("abc");
        System.out.println("修改前的：" + s.hashCode());

        //第一种：替换(不可取)
        //s.replaceAll("abc", "abcd");

        //第二种：追加(不可取)
        //StringBuffer sb = new StringBuffer(s);
        //s = sb.append("d").toString();

        //反射(可取)
        Field[] fields = s.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i]);
        }
        Field value = s.getClass().getDeclaredField("value");
        value.setAccessible(true);
        value.set(s, "abcd".toCharArray());

        System.out.println("修改后的：" + s.hashCode());
        System.out.println("修改后的字符串：" + s);
    }
}
