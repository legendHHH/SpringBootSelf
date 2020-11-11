package com.qcl.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/11
 */
@EnableScheduling
@SpringBootApplication
@MapperScan("com.qcl.springboot.mapper")
public class DayVisitAndLiveMain {

    public static void main(String[] args) {
        SpringApplication.run(DayVisitAndLiveMain.class, args);
    }
}
