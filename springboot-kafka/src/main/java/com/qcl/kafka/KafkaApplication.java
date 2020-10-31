package com.qcl.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 主启动
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/31
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class KafkaApplication {
public static void main(String[] args) {
    SpringApplication.run(KafkaApplication.class, args);
  }
}