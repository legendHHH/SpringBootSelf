package com.example.demo.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 集合跟集合的比较
 * <p>
 * 日期的计算
 *
 * @author legend
 */
public class DataTestDemo {

    public static void method() {
        Date date = new Date("2014/1/10 18:20");
        Date date2 = new Date("2014/1/11 3:5");
        ////相差毫秒数
        long temp = date2.getTime() - date.getTime();
        //相差小时数
        long hours = temp / 1000 / 3600;
        long temp2 = temp % (1000 * 3600);
        //相差分钟数
        long mins = temp2 / 1000 / 60;
        System.out.println("date2 与 date 相差" + hours + "小时" + mins + "分钟");
    }

    private static void method2() {
        //固定的比较集合数据
        List<List<String>> toCompareStatusList = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list1.add("APPROVED");
        list1.add("APPROVED");

        List<String> list2 = new ArrayList<>();
        list2.add("INPRG");
        list2.add("INPRG");

        List<String> list3 = new ArrayList<>();
        list3.add("COMPLETED");
        list3.add("INPRG");

        List<String> list4 = new ArrayList<>();
        list4.add("PRECLOSED");
        list4.add("INPRG");

        List<String> list5 = new ArrayList<>();
        list5.add("CLOSED");
        list5.add("CLOSED");

        List<String> list6 = new ArrayList<>();
        list6.add("DRAFT");
        list6.add("APPROVED");

        List<String> list7 = new ArrayList<>();
        list7.add("WRD");
        list7.add("APPROVED");

        List<String> list8 = new ArrayList<>();
        list8.add("WPCOND");
        list8.add("APPROVED");

        List<String> list9 = new ArrayList<>();
        list9.add("WSCH");
        list9.add("APPROVED");

        List<String> list10 = new ArrayList<>();
        list10.add("PAUSE");
        list10.add("APPROVED");

        List<String> list11 = new ArrayList<>();
        list11.add("RETURNED");
        list11.add("APPROVED");

        List<String> list12 = new ArrayList<>();
        list12.add("REWORK");
        list12.add("APPROVED");

        List<String> list13 = new ArrayList<>();
        list13.add("CANCELED");
        list13.add("APPROVED");

        List<String> list14 = new ArrayList<>();
        list14.add("UNABLE");
        list14.add("APPROVED");

        toCompareStatusList.add(list1);
        toCompareStatusList.add(list2);
        toCompareStatusList.add(list3);
        toCompareStatusList.add(list4);
        toCompareStatusList.add(list5);
        toCompareStatusList.add(list6);
        toCompareStatusList.add(list7);
        toCompareStatusList.add(list8);
        toCompareStatusList.add(list9);
        toCompareStatusList.add(list10);
        toCompareStatusList.add(list11);
        toCompareStatusList.add(list12);
        toCompareStatusList.add(list13);
        toCompareStatusList.add(list14);

        List<String> dbStatus = new ArrayList<>();
        dbStatus.add("APPROVED");
        dbStatus.add("APPROVED");
        boolean contains = toCompareStatusList.contains(dbStatus);
        System.out.println(contains);
    }


    private static void method3() {
        //固定的比较集合数据
        List<List<String>> toCompareStatusList = new ArrayList<>();
        List<String> list1 = Arrays.asList("APPROVED", "APPROVED");
        List<String> list2 = Arrays.asList("INPRG", "INPRG");
        List<String> list3 = Arrays.asList("COMPLETED", "INPRG");
        List<String> list4 = Arrays.asList("PRECLOSED", "INPRG");
        List<String> list5 = Arrays.asList("CLOSED", "CLOSED");
        List<String> list6 = Arrays.asList("DRAFT", "APPROVED");
        List<String> list7 = Arrays.asList("WRD", "APPROVED");
        List<String> list8 = Arrays.asList("WPCOND", "APPROVED");
        List<String> list9 = Arrays.asList("WSCH", "APPROVED");
        List<String> list10 = Arrays.asList("PAUSE", "APPROVED");
        List<String> list11 = Arrays.asList("RETURNED", "APPROVED");
        List<String> list12 = Arrays.asList("REWORK", "APPROVED");
        List<String> list13 = Arrays.asList("CANCELED", "APPROVED");
        List<String> list14 = Arrays.asList("UNABLE", "APPROVED");

        toCompareStatusList.add(list1);
        toCompareStatusList.add(list2);
        toCompareStatusList.add(list3);
        toCompareStatusList.add(list4);
        toCompareStatusList.add(list5);
        toCompareStatusList.add(list6);
        toCompareStatusList.add(list7);
        toCompareStatusList.add(list8);
        toCompareStatusList.add(list9);
        toCompareStatusList.add(list10);
        toCompareStatusList.add(list11);
        toCompareStatusList.add(list12);
        toCompareStatusList.add(list13);
        toCompareStatusList.add(list14);

        List<String> dbStatus = new ArrayList<>();
        dbStatus.add("WSCH");
        dbStatus.add("APPROVED");
        boolean contains = toCompareStatusList.contains(dbStatus);
        System.out.println(contains);
        System.out.println("..." + dbStatus.get(0));
    }


    public static void main(String[] args) {
        method();
        method2();
        method3();
    }
}
