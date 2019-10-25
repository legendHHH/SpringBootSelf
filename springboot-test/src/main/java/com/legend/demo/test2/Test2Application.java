package com.legend.demo.test2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Springboot应用的入口
 */
//@SpringBootApplication  //使用组合注解 (等价于下面的两个注解 + @SpringBootConfiguration--> @Configuration)
@EnableAutoConfiguration  //启用springboot 应用的自动配置
@ComponentScan  // 类似于 <context:component-scan base-package="">  扫描该类所在的包以及它的子孙包
public class Test2Application {

    public static void main(String[] args) {
        SpringApplication.run(Test2Application.class, args);
    }

}
