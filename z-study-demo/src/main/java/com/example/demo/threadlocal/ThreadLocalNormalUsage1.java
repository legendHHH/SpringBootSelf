package com.example.demo.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 三十个线程分别使用自己的SimpleDateFormat
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/22
 */
public class ThreadLocalNormalUsage1 {

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String myDate = new ThreadLocalNormalUsage1().myDate(finalI);
                    System.out.println(finalI + "   1===>" + myDate);
                }
            }).start();
        }

    }

    private String myDate(int seconds) {
        //参数的单位是毫秒  1970.1.1 00:00:00 GMT 计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }
}
