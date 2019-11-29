package com.legend.springboot.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoUtils {

    /**
     * https://www.fenggoudy.com
     */
    public static String getVideoUrlForFengGou(String apiUrl, String refeurl) throws Exception {
        // 参数详情 https://www.fenggoudy.com 采集
        apiUrl = "https://jx33.178du.com/jx.php?do=h5&id=7aa7f493692edb4a.mp4";
        refeurl = "https://www.fenggoudy.com";
        Document html = null;
        try {
            html = Jsoup.connect(apiUrl).header("referer", refeurl).get();
        } catch (IOException e) {
            throw e;
        }

        String videoUrl = getHtmlVideoStr(html.outerHtml()).get("src");

        System.out.print("------原始数据：" + getHtmlVideoStr(html.outerHtml()));

        if (videoUrl.contains("download.weiyun.com")) {
            return videoUrl;
        } else {
            throw new Exception("数据解析问题");
        }

    }

    /**
     * 得到html中的 video src
     */
    public static Map<String, String> getHtmlVideoStr(String htmlStr) {

        Map<String, String> pics = new HashMap<String, String>();
        String regEx_video = "<video.*src\\s*=\\s*(.*?)[^>]*?>";
        Pattern p = Pattern.compile(regEx_video, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlStr);
        String video = "";
        Map<String, String> maps = new HashMap<String, String>();
        while (m.find()) {
            video = m.group();
            Matcher mPoster = Pattern.compile("poster\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(video);
            Matcher mSrc = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(video);
            String poster = "";
            String src = "";
            while (mPoster.find()) {
                poster = mPoster.group(1);
            }
            while (mSrc.find()) {
                src = mSrc.group(1);
            }
            maps.put("poster", poster);
            maps.put("src", src);
            maps.put("srcs", src);
        }
        return maps;

    }
}
