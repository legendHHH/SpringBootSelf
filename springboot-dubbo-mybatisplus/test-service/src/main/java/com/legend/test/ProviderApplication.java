package com.legend.test;

import com.legend.test.config.EmbeddedZooKeeper;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@DubboComponentScan(basePackages = "com.legend.test.service.impl")
@ComponentScan(basePackages = {"com.legend.test.service.impl", "com.legend.test.config", "com.legend.test.mapper"})
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        new EmbeddedZooKeeper(2182, false).start();
        SpringApplication app = new SpringApplication(ProviderApplication.class);
        app.run(args);
    }

}