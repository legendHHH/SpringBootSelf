package com.example.demo.test;

import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 有序数组去重
 * [0, 1, 1, 2, 3, 3]
 * [0, 1, 2, 3]
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/9/11
 */
public class RemoveArrays {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(0, 1, 1, 2, 3, 3);
        List<Integer> res = list.stream().distinct().collect(Collectors.toList());
        System.out.println("第一种处理方式：" + res.toString());
        System.out.println("第二种处理方式：" + removeDuplicated(list).toString());
    }


    public static List<Integer> removeDuplicated(List<Integer> data) {
        if (data.size() == 0) {
            return Lists.newArrayList();
        }

        if (data.size() == 1) {
            System.out.println("当前数组长度只有一个元素,无序去重");
            return data;
        }

        List<Integer> resultList = new ArrayList<>();
        //0
        Integer firstNumber = data.get(0);
        //0
        Integer lastNumber = data.get(0);

        for (int i = 0; i < data.size(); i++) {
            if (!lastNumber.equals(data.get(i))) {
                lastNumber = data.get(i);
                resultList.add(data.get(i));
            }
        }

        return resultList;
    }
}
