package com.legend.retrofit;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 主启动类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/2
 */
@SpringBootApplication
@RetrofitScan("com.github.lianjiatech.retrofit.spring.boot.test")
public class RetrofitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetrofitApplication.class, args);
    }
}
