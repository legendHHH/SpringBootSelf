package com.example.demo.datatime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTest {

    public static final List<String> TITLE_CODE_LIST = Arrays.asList("MRS.", "MISS", "MS.");


    public static void main(String[] args) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 范围开始时间
        Date startTime = dateFormat.parse("2020-12-12 00:00:00");

        // 范围结束时间
//        Date endTime = null;
        Date endTime = dateFormat.parse("2022-12-31 14:00:00");


        //待校验时间
        Date offenceDateTime = dateFormat.parse("2022-12-31 13:12:00");
//        Date offenceDateTime = null;


        Boolean flag = checkDateIsBetweenDate(offenceDateTime, startTime, endTime);


        /*boolean flag1 = true;
        boolean flag2 = true;
        if (startTime != null) {
            flag1 = compare(startTime, offenceDateTime) <= 0;
        }
        if (endTime != null) {
            flag2 = compare(endTime, offenceDateTime) >= 0;
        }
        boolean flag = flag1 && flag2;

        System.out.println(flag1);
        System.out.println(flag2);*/

        if (!flag) {
            System.out.println("当前时间不在范围内");
        } else {
            System.out.println("当前时间在范围内");
        }
    }




    /*public static void main(String[] args) {
        String input = "Hello!123 World@4    56 ";
        String output = input.replaceAll("[^a-zA-Z0-9]", "");

        System.out.println(output);



        String t = "MRS. ";
        boolean contains = TITLE_CODE_LIST.contains(t.trim());
        System.out.println(contains);
        Date currentDate = new Date();
//        String dateStr = dateToString (currentDate, "YYYYMM");
//        String dateStr2 = dateToString (currentDate, "yyyyMM");
//        System.out.println(dateStr);
//        System.out.println(dateStr2);


        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);

        // 添加一天
        cal.add(Calendar.DAY_OF_MONTH, 79);

        Date newDate = cal.getTime();
        System.out.println(newDate);

        String dateStr = dateToString(newDate, "YYYYMM");
        String dateStr2 = dateToString(newDate, "yyyyMM");
        System.out.println(dateStr);
        System.out.println(dateStr2);
    }*/

    public static String dateToString(Date date, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        String str = formatter.format(date);
        return str;
    }

    public static Boolean checkDateIsBetweenDate(Date offenceDateTime, Date startTime, Date endTime){
        if (offenceDateTime == null) {
            return false;
        }

        boolean flag1 = true;
        boolean flag2 = true;

        if (startTime != null) {
            flag1 = compare(startTime, offenceDateTime) <= 0;
        }
        if (endTime != null) {
            flag2 = compare(endTime, offenceDateTime) >= 0;
        }
        return (flag1 && flag2);
    }

    public static int compare(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return 0;
        } else if (date1 == null && date2 != null) {
            return -1;
        } else if (date1 != null && date2 == null) {
            return 1;
        } else {
            if (date1.equals(date2)) {
                return 0;
            } else if (date1.before(date2)) {
                return -1;
            } else if (date1.after(date2)) {
                return 1;
            } else {
                return 0;
            }
        }
    }


}
