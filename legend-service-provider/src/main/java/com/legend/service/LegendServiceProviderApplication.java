package com.legend.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.legend.service.mapper") //Mapper接口的包扫描
public class LegendServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LegendServiceProviderApplication.class, args);
    }

}
