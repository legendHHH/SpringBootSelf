package com.example.demo.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间计算
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/8
 */
public class TimeTest {

    public static void main(String[] args) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //天数差
        try {
            Date fromDate1 = simpleFormat.parse("2021-01-01 00:00:00");
            Date toDate1 = simpleFormat.parse("2021-06-30 23:59:59");
            long from1 = fromDate1.getTime();
            long to1 = toDate1.getTime();
            //天数1095
            int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));
            if (days > 90) {
                System.out.println("大于三个月");
            }
            //年数
            double years = (double) ((to1 - from1) / (1000 * 60 * 60 * 24 * 30));
            System.out.println("两个时间之间的天数差为：" + days);
            System.out.println("两个时间之间的年数差为：" + years);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        //// 当前系统时间
        System.out.println(df.format(date));
        Date newDate = stepMonth(date, 7);
        System.out.println("当前时间加了7个月之后的日期datetime：" + newDate.getTime());
        System.out.println("当前时间加了7个月之后的日期：" + df.format(newDate));

    }

    public static Date stepMonth(Date sourceDate, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MONTH, month);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        System.out.println("stepMonth:" + c.getTime().getTime());
        return c.getTime();
    }
}
