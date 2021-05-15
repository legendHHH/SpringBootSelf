package com.example.demo.WebMagic;

import us.codecraft.webmagic.Site;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/15
 */
public class GetPhotoSpider {

    /**
     * 设置参数
     * 爬虫相关配置，如抓取间隔、休息时间等
     */
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(3000);

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        // 这里只爬取第一页的壁纸，如果要爬取其他页数修改for循环参数即可
        for (int i = 1; i <= 1; i++) {
            // 启动爬虫
            /*Spider.create(new GetPhotoSpider())
                    // 添加初始化的URL
                    .addUrl("https://wallhaven.cc/toplist?page=" + i)
                    // 使用单线程
                    .thread(1)
                    // 运行
                    .run();*/
        }
    }
}
