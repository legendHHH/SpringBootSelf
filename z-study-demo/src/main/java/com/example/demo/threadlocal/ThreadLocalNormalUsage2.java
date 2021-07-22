package com.example.demo.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一千个线程打印日期的任务 用线程池来执行
 * 需要引入线程池
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/22
 */
public class ThreadLocalNormalUsage2 {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            //任务放到线程池
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String myDate = new ThreadLocalNormalUsage2().myDate(finalI);
                    System.out.println(finalI + "   1===>" + myDate);
                }
            });
        }
        threadPool.shutdown();
    }

    private String myDate(int seconds) {
        //参数的单位是毫秒  1970.1.1 00:00:00 GMT 计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }
}
