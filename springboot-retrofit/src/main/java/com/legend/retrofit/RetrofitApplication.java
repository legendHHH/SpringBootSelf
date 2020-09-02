package com.legend.retrofit;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author legend
 */
@SpringBootApplication
@RetrofitScan("com.github.lianjiatech.retrofit.spring.boot.test")
public class RetrofitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetrofitApplication.class, args);
    }
}
