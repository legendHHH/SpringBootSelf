package com.example.demo.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * map下标对应26个字母
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/1/19
 */
public class TestDemo {

    private final static SimpleDateFormat SP = new SimpleDateFormat("yyyy-MM-dd");
    private static AtomicInteger FIRST = new AtomicInteger(0);

    public static void main(String[] args) {
        int key = 0;
        char value = 'A' - 1;
        char end = 'Z';
        Map map = Maps.newHashMap();
        while (value < end) {
            value++;
            // 跳过这些 字母
            // if (key == 'I' || key == 'Q' || key == 'O') {
            // continue;
            // }
            map.put(key, value);
            key++;
        }
        for (int i = 1; i <= 12; i++) {
            FIRST.incrementAndGet();
            getDayListOfMonth(2022, i);
        }
        streamFileRead("2022-111.json", 3);

    }

    /**
     * java获取当月所有的日期和星期集合
     *
     * @return
     */
    public static Object getDayListOfMonth(int curYear, int curMonth) {

        List<String> dateList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(31);
        Map<String, Object> map2 = new HashMap<>(31);
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        aCalendar.set(Calendar.DATE, 1);
        //年份
        aCalendar.set(Calendar.YEAR, curYear);
        //月份
        aCalendar.set(Calendar.MONTH, curMonth - 1);
        int day = aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String monthStr = "0";
        if (curMonth < 10) {
            monthStr = "0" + curMonth;
        } else {
            monthStr = String.valueOf(curMonth);
        }
        for (int i = 1; i <= day; i++) {
            String days = "0";
            if (i < 10) {
                days = "0" + i;
            } else {
                days = String.valueOf(i);
            }
            String completeDate = curYear + "-" + monthStr + "-" + days;
            try {
                Date date = SP.parse(completeDate);
                String week = getWeek(date, "en_US");
                map.put(completeDate, week);

                dateList.add(completeDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String dateStr = dateList.stream().map(String::valueOf).collect(Collectors.joining(","));

        //请求判断工作日、节假日、周末
        String response = request(dateStr);
        Map<String, Object> responseMap = JSONObject.parseObject(response, Map.class);
        System.out.println("结果数据：" + JSONObject.toJSONString(responseMap));
        //将数据写入json文件
        try {
            writeDataToFile("2022", JSONObject.toJSONString(responseMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map2;
    }

    /**
     * 写入数据到文件
     *
     * @param fileName
     * @param data
     * @return
     * @throws IOException
     */
    public static String writeDataToFile(String fileName, String data) throws Exception {
        if (fileName == null) {
            throw new RuntimeException("文件后缀名不规范");
        }
        //此处设置为true即可追加
        FileWriter out = new FileWriter("D:/2022-111.json", true);
        if (FIRST.get() == 1) {
            out.write("[");
        }
        //往文件写入
        out.write(data);

        //继续追加
        if (FIRST.get() == 12) {
            out.write("]");
            //刷新IO内存流
            out.flush();
            //关闭
            out.close();
        } else {
            out.write(",");
            //刷新IO内存流
            out.flush();
        }
        return "写入成功";
    }

    /**
     * 使用stream流来读取文件
     *
     * @return
     */
    public static String streamFileRead(String fileName, int month) {
        byte[] bytes = new byte[0];
        try {
            //bytes = Files.readAllBytes(Paths.get(fileName));
            bytes = Files.readAllBytes(ResourceUtils.getFile("classpath:"+fileName).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content = new String(bytes, StandardCharsets.UTF_8);
        JSONArray jsonArray = JSONArray.parseArray(content);
        System.out.println(jsonArray.get(month - 1));
        return content;
    }

    private static String getWeek(Date date, String language) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String[] weeksOfEnglish = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekIndex < 0) {
            weekIndex = 0;
        }
        //默认英文en-US
        if ("zh_CN".equals(language)) {
            return weeks[weekIndex];
        }
        return weeksOfEnglish[weekIndex];
    }


    /**
     * 判断当前是否为节假日： 0 上班  1周末 2节假日
     *
     * @param httpArg :参数
     * @return 返回结果
     */
    public static String request(String httpArg) {
        String httpUrl = "http://tool.bitefu.net/jiari/";
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?d=" + httpArg;
        System.out.println(httpUrl);
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
