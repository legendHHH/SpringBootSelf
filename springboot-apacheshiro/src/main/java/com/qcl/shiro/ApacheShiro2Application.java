package com.qcl.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 主启动
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/21
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.qcl.shiro.mapper"})
public class ApacheShiro2Application {

    public static void main(String[] args) {
        SpringApplication.run(ApacheShiro2Application.class, args);
    }
}
