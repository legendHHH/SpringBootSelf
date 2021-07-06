package com.qcl.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * main函数
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/5
 */
@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchMain {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchMain.class, args);
    }
}
