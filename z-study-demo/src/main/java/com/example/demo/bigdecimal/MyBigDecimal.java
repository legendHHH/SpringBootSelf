package com.example.demo.bigdecimal;

import java.math.BigDecimal;

/**
 * BigDecimal的操作
 *
 * @author legned
 * @version 1.0
 * @description
 * @date 2021/9/24
 */
public class MyBigDecimal {

    public static void main(String[] args) {
        BigDecimal s = new BigDecimal(100).divide(new BigDecimal(130), 3, BigDecimal.ROUND_UP).multiply(new BigDecimal(120));
        BigDecimal s2 = new BigDecimal(100).divide(new BigDecimal(130), 5, BigDecimal.ROUND_UP).multiply(new BigDecimal(120));
        System.out.println(s);
        System.out.println(s2);
        System.out.println(s.longValue());
    }
}
