package com.example.demo.string;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串拼接合List对于字符串添加单引号
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/25
 */
public class StringJoinSingleQuotes {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c");
        String str = (list + "").replace("[", "").replace("]", "");
        System.out.println(list);
        System.out.println(str);

        String strNew = list.stream().map(s -> "'" + s + "'").collect(Collectors.joining(","));
        System.out.println(strNew);
    }
}
