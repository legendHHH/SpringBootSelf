package com.example.demo.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * AtomicLongTest测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/9/27
 */
public class AtomicLongTest {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(100);

    private static AtomicLong atomic = new AtomicLong();

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final int temp = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {

                    if (atomic.incrementAndGet() > 10) {
                        System.out.println("Request rejected ...");
                        return;
                    }

                    System.out.println("Request processing ...");
                    atomic.decrementAndGet();

                    list.add(temp);
                }
            });
        }
        System.out.println(list.toString());
        threadPool.shutdown();

    }
}
