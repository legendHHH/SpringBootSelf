package com.example.demo.java8;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Map的foreach
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/12
 */
public class MapForeach {

    public static void main(String[] args) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("h", 1);
        objectMap.put("i", 1);
        objectMap.put("j", 1);
        objectMap.put("k", 1);

        //第一种
        objectMap.forEach((k, v) -> {
            System.out.println(k + "------- " + v);
        });

        //第二种
        objectMap.forEach(new BiConsumer<String, Object>() {
            @Override
            public void accept(String k, Object v) {
                System.out.println(k + "," + v);
            }
        });
    }
}
