package com.example.demo.future;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/3
 */
public class CompletableFutureTest {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String test = "Test_" + i;
            final int a = i;

            CompletableFuture<Boolean> cf = CompletableFuture.supplyAsync(() -> doPost(test));
            cf.thenRun(() -> System.out.println("thenRun：" + a));
        }
    }

    private static boolean doPost(String t) {
        System.out.println("doPost：" + t);
        return true;
    }
}
