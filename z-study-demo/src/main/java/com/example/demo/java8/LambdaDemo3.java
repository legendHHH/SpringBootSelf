package com.example.demo.java8;

import com.example.demo.java8.entity.Dish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @author legend
 */
public class LambdaDemo3 {

    private static Logger log = LoggerFactory.getLogger(LambdaDemo3.class);

    public static void main(String[] args) {
        List<Dish> appleList = new ArrayList<>(); // 假装数据是从库里查出来的
        for (Dish apple : appleList) {
            apple.setCalories((int) (5.0 * apple.getCalories() / 1000));
        }
    }
}
