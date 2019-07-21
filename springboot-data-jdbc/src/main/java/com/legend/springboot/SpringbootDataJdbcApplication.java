package com.legend.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SpringbootDataJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDataJdbcApplication.class, args);
    }

}
