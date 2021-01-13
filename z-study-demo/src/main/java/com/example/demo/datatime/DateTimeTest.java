package com.example.demo.datatime;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * LocalDate、LocalTime、LocalDateTime
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/13
 */
public class DateTimeTest {

    /**
     * 未格式化的当前时间
     */
    private static final Date CURRENTDATE = new Date();

    public static void main(String[] args) {
        operationYMDMethod();
        //instantMethod();
        //localDateTimeMethod();
        //localTimeGetYMSMethod();
        //localTimeMethod();
        //localDateGetYMDMethod();
        //localDateMethod(2019,12,30);
        //localDateNowMethod();
        //simpleDateFormatMethod();

        long g = 2998L;
        long g2 = 1000;

        BigDecimal divide = new BigDecimal(g).divide(new BigDecimal(g2));
        System.out.println(divide);
        String x = divide.toString();
        System.out.println(x);


    }

    /**
     * 增加、减少年数、月数、天数等 以LocalDateTime
     *
     * @return
     */
    public static String operationYMDMethod() {
        LocalDateTime localDateTime = LocalDateTime.of(2019, Month.SEPTEMBER, 10,
                14, 46, 56);

        //增加一年
        localDateTime = localDateTime.plusYears(1);
        localDateTime = localDateTime.plus(1, ChronoUnit.YEARS);

        //减少一个月
        localDateTime = localDateTime.minusMonths(1);
        localDateTime = localDateTime.minus(1, ChronoUnit.MONTHS);

        localDateTime.withYear(2021);
        localDateTime.with(ChronoField.YEAR, 2022);

        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return null;
    }

    /**
     * 获取秒数
     *
     * @return
     */
    public static String instantMethod() {
        Instant instant = Instant.now();
        long currentSecond = instant.getEpochSecond();
        //等同于System.currentTimeMillis();
        long currentMilli = instant.toEpochMilli();
        System.out.println("秒数：" + currentSecond + " 毫秒数：" + currentMilli);
        return null;
    }

    /**
     * 获取年月日时分秒，等于LocalDate+LocalTime
     *
     * @return
     */
    public static String localDateTimeMethod() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime1 = LocalDateTime.of(2019, Month.SEPTEMBER, 10, 14, 46, 56);
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println(localDateTime1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return null;
    }

    /**
     * 获取几点几分几秒
     *
     * @return
     */
    public static String localTimeGetYMSMethod() {
        LocalTime localTime = LocalTime.now();
        //获取小时
        int hour = localTime.getHour();
        int hour1 = localTime.get(ChronoField.HOUR_OF_DAY);
        //获取分
        int minute = localTime.getMinute();
        int minute1 = localTime.get(ChronoField.MINUTE_OF_HOUR);
        //获取秒
        int second = localTime.getSecond();
        int second1 = localTime.get(ChronoField.SECOND_OF_MINUTE);
        System.out.println("hour：" + hour + "minute：" + minute + "second：" + second);
        System.out.println("hour1：" + hour1 + "minute1：" + minute1 + "second1：" + second1);
        return null;
    }

    /**
     * 获取几点几分几秒
     *
     * @return
     */
    public static String localTimeMethod() {
        LocalTime now = LocalTime.now();
        System.out.println(now);
        return null;
    }

    /**
     * 当前时间获取年、月、日、星期几
     *
     * @return
     */
    public static String localDateGetYMDMethod() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int year1 = now.get(ChronoField.YEAR);

        int month = now.getMonthValue();
        int month1 = now.get(ChronoField.MONTH_OF_YEAR);

        int dayOfMonth = now.getDayOfMonth();
        int dayOfMonth1 = now.get(ChronoField.DAY_OF_MONTH);

        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int dayofweek = dayOfWeek.get(ChronoField.DAY_OF_WEEK);
        System.out.println("year：" + year + " month：" + month + " day：" + dayOfMonth + " dayofweek：" + dayofweek);
        System.out.println("year1：" + year1 + " month1：" + month1 + " day1：" + dayOfMonth1 + " dayofweek：" + dayofweek);
        return null;
    }

    /**
     * 构造指定的年月日
     *
     * @return
     */
    public static String localDateMethod(int year, int month, int dayOfMonth) {
        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
        System.out.println(localDate);
        return null;
    }

    /**
     * 只会当前时间获取年月日
     *
     * @return
     */
    public static String localDateNowMethod() {
        LocalDate now = LocalDate.now();
        System.out.println(now);
        return null;
    }

    /**
     * SimpleDateFormat是线程不安全的
     *
     * @return
     */
    public static String simpleDateFormatMethod() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateFormatStr = sdf.format(CURRENTDATE);
        System.out.println("hello：" + CURRENTDATE);
        System.out.println("hello2：" + currentDateFormatStr);
        return null;
    }
}
