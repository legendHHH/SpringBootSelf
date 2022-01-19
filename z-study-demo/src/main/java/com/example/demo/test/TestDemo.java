package com.example.demo.test;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * map下标对应26个字母
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/1/19
 */
public class TestDemo {

    public static void main(String[] args) {
        int key = 0;
        char value = 'A' - 1;
        char end = 'Z';
        Map map = Maps.newHashMap();
        while (value < end) {
            value++;
            // 跳过这些 字母
            // if (key == 'I' || key == 'Q' || key == 'O') {
            // continue;
            // }
            map.put(key, value);
            key++;
        }
        System.out.println(JSONObject.toJSONString(map));
    }
}
