package com.leyou.httpdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * Httpclient客户端发送请求测试
 * EnableScheduling启动定时任务
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/1/3
 */
@SpringBootApplication
@EnableScheduling
public class HttpDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpDemoApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
