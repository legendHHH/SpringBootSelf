package com.legend.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 注解EnableScheduling 开启定时任务
 * 注解EnableAsync 开启异步
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/12
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SpringbootLoggingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLoggingApplication.class, args);
    }

}
