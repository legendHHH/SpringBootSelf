package com.qcl.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 主启动
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/28
 */
@SpringBootApplication
public class MongoDbMain {

    public static void main(String[] args) {
        SpringApplication.run(MongoDbMain.class, args);
    }
}
