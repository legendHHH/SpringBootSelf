package com.legend.spring.aop;

/**
 * aop业务逻辑类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/4
 */
public class MathCalculator {

    public int div(int i, int j) {
        System.out.println("MathCalculator.....div....");
        return i / j;
    }
}
