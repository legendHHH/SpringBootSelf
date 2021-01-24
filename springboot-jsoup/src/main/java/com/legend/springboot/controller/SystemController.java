package com.legend.springboot.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * jsonp 爬取页面的元素
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/24
 */
@Controller
public class SystemController {

    private static Logger logger = LoggerFactory.getLogger(SystemController.class);

    /**
     * GetWeather
     *
     * @return
     */
    @GetMapping("/getWeather/")
    @ResponseBody
    public List<List<String>> getWeather() {
        String city = "101190101";
        String url = "http://www.weather.com.cn/weather/" + city + ".shtml";
        //用于存储天气状况
        List<String> lweather = new LinkedList<>();
        //用于存储日期
        List<String> lweatherData = new LinkedList<>();
        //用于存储温度
        List<String> lweatherTempture = new LinkedList<>();
        //用于存储风向
        List<String> lweatherWin = new LinkedList<>();
        List<List<String>> lweatherAll = new LinkedList<>();

        try {
            Document document = Jsoup.connect(url).timeout(5000).get();
            logger.info("通过url获取到的内容：{}", document);
            //Elements content = document.getElementsByClass("t clearfix");
            Elements content = document.select("[class=t clearfix]");
            logger.info("通过class选择器获取到的内容：{}", content);
            for (Element e : content) {
                Document conDoc = Jsoup.parse(e.toString());
                Elements cru = conDoc.getElementsByClass("crumbs fl");
                Elements sky = content.select("li[class^=sky skyid lv]");

                for (Element e1 : sky) {
                    //此处用于获取日期天气
                    Elements weatherData = e1.select("h1");
                    lweatherData.add(weatherData.text());
                    //用于获取天气信息
                    Elements weather = e1.select("p[class=wea]");
                    lweather.add(weather.text());
                    //用于获取天气温度
                    Elements weatherTempture = e1.select("p[class=tem]");
                    lweatherTempture.add(weatherTempture.text());
                    //用于获取风向
                    Elements weatherWin = e1.select("span");
                    lweatherWin.add(weatherWin.attr("title"));
                }
                logger.info("lweather:{}", lweather);
                logger.info("lweatherData:{}", lweatherData);
                logger.info("lweatherWin:{}", lweatherWin);

                lweatherAll.add(lweather);
                lweatherAll.add(lweatherData);
                lweatherAll.add(lweatherWin);
                return lweatherAll;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
