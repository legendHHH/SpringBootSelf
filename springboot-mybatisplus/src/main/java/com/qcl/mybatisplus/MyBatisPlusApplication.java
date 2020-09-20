package com.qcl.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 主启动
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/20
 */
@SpringBootApplication
//@MapperScan({"com.example.demo.mapper", "com.example.demo.hello.mapper"})
@MapperScan({"com.qcl.mybatisplus.mapper"})
public class MyBatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlusApplication.class, args);
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.qcl.mybatisplus.hello");
        return mapperScannerConfigurer;
    }
}
