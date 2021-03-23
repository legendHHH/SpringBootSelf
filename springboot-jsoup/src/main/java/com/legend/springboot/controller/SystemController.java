package com.legend.springboot.controller;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STCellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 1. 获取上传的url列表
     * 2. 遍历获取url对应页面的HTML源码
     * 3. 提取对应的商品信息字段
     * 4. 输出的excel
     */
    @PostMapping("/excel/upload")
    public void uploadExcel(HttpServletResponse response, @RequestParam("file") MultipartFile file) {
        try {
            // 读取Excel文件
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            // 读取Excel工作表
            XSSFSheet sheet = workbook.getSheetAt(0);

            // 创建输出Excel文件
            XSSFWorkbook newWorkbook = new XSSFWorkbook();
            // 创建Sheet
            XSSFSheet newSheet = newWorkbook.createSheet();
            // 创建标题行
            XSSFRow titleRow = newSheet.createRow(0);
            // 设置标题行
            XSSFCell cell1 = titleRow.createCell(0, STCellType.INT_STR);
            cell1.setCellValue("商品编码");
            XSSFCell cell2 = titleRow.createCell(1, STCellType.INT_STR);
            cell2.setCellValue("商品名称");
            XSSFCell cell3 = titleRow.createCell(2, STCellType.INT_STR);
            cell3.setCellValue("商品分类");
            // 设置宽度
            newSheet.setColumnWidth(0, 2560);
            newSheet.setColumnWidth(1, 25600);
            newSheet.setColumnWidth(2, 5120);
// 遍历获取HTML源码，提取信息
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                // 获取行
                XSSFRow row = sheet.getRow(i);
                // 获取列
                XSSFCell cell = row.getCell(0);
                // 获取url
                String url = cell.getStringCellValue();

                // 输出的Excel创建行
                XSSFRow newRow = newSheet.createRow(i + 1);

                // 判断url不为空并且包含http
                if (!url.isEmpty() && url.contains("http")) {
                    // 获取商品信息集合
                    Map<String, String> data = getProductInfo(url);

                    // 输出商品信息到Excel表
                    if (data != null) {
                        XSSFCell cellOne = newRow.createCell(0, STCellType.INT_STR);
                        cellOne.setCellValue(data.get("sku"));
                        XSSFCell cellTwo = newRow.createCell(1, STCellType.INT_STR);
                        cellTwo.setCellValue(data.get("name"));
                        XSSFCell cellThree = newRow.createCell(2, STCellType.INT_STR);
                        cellThree.setCellValue(data.get("cat"));
                    }
                }
                // 打印调试
                System.out.println("\n内容是：" + url);
            }


            // 下载excel
            response.setContentType("application/octet-stream");
            // 以时间戳命名
            String fileName = System.currentTimeMillis() + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.flushBuffer();

            // 输出excel
            newWorkbook.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提取商品信息
     */
    private Map<String, String> getProductInfo(String url) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        // 模拟浏览器浏览
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:60.0) Gecko/20100101 Firefox/60.0");
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        // The underlying HTTP connection is still held by the response object
        // to allow the response content to be streamed directly from the network socket.
        // In order to ensure correct deallocation of system resources
        // the user MUST call CloseableHttpResponse#close() from a finally clause.
        // Please note that if response content is not fully consumed the underlying
        // connection cannot be safely re-used and will be shut down and discarded
        // by the connection manager.

        // 结果集合
        Map<String, String> reslut = null;

        //获取响应状态码
        int StatusCode = response1.getStatusLine().getStatusCode();

        try {

            HttpEntity entity1 = response1.getEntity();

            //如果状态响应码为200，则获取html实体内容或者json文件
            if (StatusCode == 200) {
                String html = EntityUtils.toString(entity1, Consts.UTF_8);

                // 提取HTML得到商品信息结果
                reslut = getData(html);

                // 消耗掉实体
                EntityUtils.consume(response1.getEntity());
            } else {
                //否则，消耗掉实体
                EntityUtils.consume(response1.getEntity());
            }
        } finally {
            response1.close();
        }
        return reslut;
    }

    /**
     * 获取数据通过html
     *
     * @param html
     * @return
     * @throws Exception
     */
    private static Map<String, String> getData(String html) throws Exception {
        //获取的数据，存放在集合中
        Map<String, String> data = new HashMap<>(16);

        //采用Jsoup解析
        Document doc = Jsoup.parse(html);

        //获取html标签中的内容
        // 标题
        String name = doc.select("div[class=sku-name]").text();
        if (name != null) {
            data.put("name", name);
        }

        // sku
        String sku = "";
        Elements elements = doc.select("a[data-sku]");
        for (Element ele : elements) {
            if (ele.hasAttr("data-sku")) {
                sku = ele.attr("data-sku");
                break;
            }
        }

        if (sku != null) {
            data.put("sku", sku);
        }

        String cat = doc.select("a[clstag=shangpin|keycount|product|mbNav-1]").text();

        if (cat != null) {
            data.put("cat", cat);
        }
        logger.info("{}---------{}---------{}", sku, cat, name);

        //返回数据
        return data;
    }


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
