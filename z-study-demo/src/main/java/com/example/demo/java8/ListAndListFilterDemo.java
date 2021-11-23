package com.example.demo.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * list和list之间的交集、并集、差集等
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
        System.out.println("差集：" + listC.toString());

        //交集(list内容变为list和list1都存在的对象)
        List<Integer> listA = new ArrayList<>();
        listA.add(124);
        listA.add(12);
        listA.add(126);
        listA.add(1);

        List<Integer> listB = new ArrayList<>();
        listB.add(12);
        listB.add(1294);
        listB.add(1260);
        listB.add(1);
        //差集(A-B 用A的集合减去B中跟A相同的数据)
        //listA.removeAll(listB);
        //System.out.println("差集第二种方式：" + listA.toString());

        //取交集(A、B集合中相同的数据)
        listA.retainAll(listB);
        System.out.println("交集：" + listA.toString());

        //并集(为了去重,listA先取差集,然后追加全部的listB)
        //listA.removeAll(listB);
        //listA.addAll(listB);
        System.out.println("并集：" + listA.toString());

    }
}
