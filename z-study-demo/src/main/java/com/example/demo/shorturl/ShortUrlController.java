package com.example.demo.shorturl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Random;

/**
 * 短链接生成控制器
 * 发号器随机生成
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/15
 */
@RestController
@RequestMapping("/shortUrl")
public class ShortUrlController {

    private String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 短链url域名前缀
     */
    private String shortUrlPrefix = "http://a.cn/";
    private HashMap<String, String> map = new HashMap<>();

    @PostMapping("/getShortUrl")
    public String getShortUrl(String longUrl) {
        String key = creatKey();
        while (map.containsKey(key)) {
            key = creatKey();
        }
        map.put(key, longUrl);
        return shortUrlPrefix + key;
    }

    @PostMapping("/getLongUrl")
    public String getLongUrl(String shortUrl) {
        return map.get(shortUrl.replace(shortUrlPrefix, ""));
    }

    private String creatKey() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(BASE62.charAt(rand.nextInt(62)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int i = 9999;
        int j = 9999;
        System.out.println(i--);
        System.out.println(i);

        System.out.println(++j);
        System.out.println(j);
    }
}
