package com.example.demo.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一千个线程打印日期的任务 用线程池来执行
 * 最好的解决方案
 * 引入ThreadLocal给每个线程分配自己的dateFormat对象,保证了线程安全,高效利用内存
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/24
 */
public class ThreadLocalNormalUsage5 {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            //任务放到线程池
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String myDate = new ThreadLocalNormalUsage5().myDate(finalI);
                    System.out.println(finalI + "   1===>" + myDate);
                }
            });
        }
        threadPool.shutdown();
    }

    private String myDate(int seconds) {
        //参数的单位是毫秒  1970.1.1 00:00:00 GMT 计时
        Date date = new Date(1000 * seconds);

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        //使用ThreadLocal解决线程安全问题
        SimpleDateFormat simpleDateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
        return simpleDateFormat.format(date);
    }
}


/**
 * 创建ThreadLocal对象
 */
class ThreadSafeFormatter {

    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return simpleDateFormat;
        }
    };

    //使用lambda表达式创建
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal2 = ThreadLocal.withInitial(() -> {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    });
}