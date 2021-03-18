package com.example.demo.equals;

import com.example.demo.string.Person;

import java.util.HashSet;
import java.util.Set;

/**
 * 1. == 和 equals
 *
 * == 比较基本引用类型+基本类型
 *    引用类型比较的是内存地址：new
 *      基本类型：比较值
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/3/18
 */
public class TestEquals {

    public static void main(String[] args) {
        String s1 = new String("abc");
        String s2 = new String("abc");
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
        Set<String> strSet = new HashSet<>();
        strSet.add(s1);
        strSet.add(s2);
        System.out.println(strSet.size());
        System.out.println(s1.hashCode() +"\t" + s2.hashCode());

        System.out.println("==============================");

        Person p1 = new Person("abc");
        Person p2 = new Person("abc");
        System.out.println(p1 == p2);
        System.out.println(p1.equals(p2));
        Set<Person> set = new HashSet<>();
        set.add(p1);
        set.add(p2);
        System.out.println(p1.hashCode() +"\t" + p2.hashCode());
        System.out.println(set.size());
    }
}
