package com.example.demo.threadlocal;

import com.example.demo.threadlocal.service.Service1;

/**
 * 演示ThreadLocal用法2：避免传递参数的麻烦
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/24
 */
public class ThreadLocalNormalUsage20 {

    public static void main(String[] args) {
        new Service1().process();
    }
}



