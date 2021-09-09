package com.example.demo.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CompletableFuture测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/3
 */
public class CompletableFutureTest {

    private static ExecutorService es1 = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        /*for (int i = 0; i < 100; i++) {
            String test = "Test_" + i;
            final int a = i;

            CompletableFuture<Boolean> cf = CompletableFuture.supplyAsync(() -> doPost(test));
            cf.thenRun(() -> System.out.println("thenRun：" + a));
        }*/

        //int i = 10 / 0;
        CompletableFuture<String> method = method();
        System.out.println(method);
    }

    private static boolean doPost(String t) {
        System.out.println("doPost：" + t);
        return true;
    }


    public static CompletableFuture<String> method() {
        /*CompletableFuture.supplyAsync(() -> {

            try {
                int i = 10 / 0;
            } catch (Exception e) {
                //e.printStackTrace();
                throw new RuntimeException("出异常了");
                //return "create pos failed";
            }
            return "create pos";
        });*/

        //CompletableFuture<String> future = new CompletableFuture<>();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {

            try {
                int i = 10 / 0;
            } catch (Exception e) {
                e.printStackTrace();
                //future.completeExceptionally(e);
                return "create pos failed";
            }
            return "create pos";
        });

        //获取异常日志
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            //return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            //return null;
        }

        System.out.println("CompletableFuture");

        return null;
    }

}
