//package com.leyou.httpdemo.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//
///**
// * 判断当前是否为节假日： 0 上班  1周末 2节假日
// *
// * @author legend
// * @version 1.0
// * @description
// * @date 2021/12/27
// */
//@Slf4j
//public class DateUtil {
//
//    /**
//     * 判断当前是否为节假日： 0 上班  1周末 2节假日
//     *
//     * @param httpArg :参数
//     * @return 返回结果
//     */
//    public static String request(String httpArg) {
//        String httpUrl = "http://tool.bitefu.net/jiari/";
//        BufferedReader reader = null;
//        String result = null;
//        StringBuffer sbf = new StringBuffer();
//        httpUrl = httpUrl + "?d=" + httpArg;
//        try {
//            URL url = new URL(httpUrl);
//            HttpURLConnection connection = (HttpURLConnection) url
//                    .openConnection();
//            connection.setRequestMethod("GET");
//            connection.connect();
//            InputStream is = connection.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            String strRead = null;
//            while ((strRead = reader.readLine()) != null) {
//                sbf.append(strRead);
//            }
//            reader.close();
//            result = sbf.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    /**
//     * 计算两个日期之间相差得天数
//     *
//     * @param firstDateStr
//     * @param secondDateStr
//     * @param useCurrentDateFlag
//     * @return
//     */
//    public static Long calculateDaysBetweenTwoDate(String firstDateStr, String secondDateStr, boolean useCurrentDateFlag) {
//        if (StringUtils.isBlank(firstDateStr)) {
//            log.error("第一个比较时间必须有值....");
//            return -1L;
//        }
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            //跨年不会出现问题
//            Date fDate = sdf.parse(firstDateStr);
//            if (useCurrentDateFlag) {
//                secondDateStr = sdf.format(new Date());
//            }
//            Date oDate = sdf.parse(secondDateStr);
//            long days = (oDate.getTime() - fDate.getTime()) / (1000 * 3600 * 24);
//            log.info("两个日期{},{} 相差得天数是：{}", firstDateStr, secondDateStr, days);
//            return days;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return 0L;
//    }
//
//    /**
//     * 判断当前日期是否大于某个日期
//     *
//     * @param date yyyy-MM-dd
//     * @return
//     */
//    public static boolean afterDate(String date) {
//        if (StringUtils.isBlank(date)) {
//            return false;
//        }
//        //Text '2021-01-01 00:00:00' could not be parsed, unparsed text found at index 10
//        if (date.contains("00:00:00")) {
//            date = date.replaceAll(" 00:00:00", "").trim();
//        }
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        //把String转为LocalDate
//        LocalDate localTime = LocalDate.parse(date, dtf);
//        //判断当前日期是否大于指定日期
//        return LocalDate.now().isAfter(localTime);
//    }
//
//
//    public static void main(String[] args) {
//        //calculateDaysBetweenTwoDate("2021-01-10", "2021-01-21", true);
//
//        boolean b = afterDate("2021-01-01 00:00:00");
//        System.out.println(b);
//
//        // 处理节假日
//        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//        String httpArg = f.format(new Date());
//        String jsonResult = request(httpArg);
//        // 0 上班  1周末 2节假日
//        if ("0".equals(jsonResult)) {
//            System.out.println("0上班");
//        }
//        if ("1".equals(jsonResult)) {
//            System.out.println("1周末");
//        }
//        if ("2".equals(jsonResult)) {
//            System.out.println("2节假日");
//        }
//    }
//}
//
