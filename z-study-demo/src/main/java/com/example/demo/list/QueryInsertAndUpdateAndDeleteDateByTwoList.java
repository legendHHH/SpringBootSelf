package com.example.demo.list;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据两个集合数据查询删除、更新、插入的数据
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/7/2
 */
public class QueryInsertAndUpdateAndDeleteDateByTwoList {

    public static void main(String[] args) {
        List<Integer> list0 = new ArrayList<>();
        //新数据
        List<Integer> list1 = new ArrayList<Integer>() {{
            this.add(1);
            this.add(2);
            this.add(3);
        }};
        //旧数据
        List<Integer> list2 = new ArrayList<Integer>() {{
            this.add(2);
            this.add(3);
            this.add(4);
        }};
        //取差集
        list0.addAll(list1);
        list0.removeAll(list2);
        System.out.println("----------新增-------------");
        list0.forEach(System.out::println);
        //取差集
        list0.clear();
        list0.addAll(list2);
        list0.removeAll(list1);
        System.out.println("----------删除-------------");
        list0.forEach(System.out::println);
        list0.clear();
        list0.addAll(list1);
        //取交集
        list0.retainAll(list2);
        System.out.println("----------更新-------------");
        list0.forEach(System.out::println);
        //取并集的方法,直接可以用 list1.addAll(list2);
        //我不需要取并集,所以只是用.addAll方法来赋值给空集合

    }

}