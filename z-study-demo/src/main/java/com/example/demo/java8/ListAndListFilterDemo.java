package com.example.demo.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * list和list之间的交集并集差急等
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/11/18
 */
public class ListAndListFilterDemo {

    public static void main(String[] args) {
        //差集
        List<Integer> list = Arrays.asList(123, 456, 1);
        List<Integer> list1 = Arrays.asList(123);
        List<Integer> listC = list.stream().filter(item -> !list1.contains(item)).collect(Collectors.toList());
        System.out.println(listC.toString());


    }
}
