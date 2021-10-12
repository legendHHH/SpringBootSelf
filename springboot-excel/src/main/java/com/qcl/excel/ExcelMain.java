package com.qcl.excel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/22
 */
@SpringBootApplication
@MapperScan("com.qcl.excel.mapper")
public class ExcelMain {

    @PostConstruct
    void started() {
        //时区设置：中国上海
        //time.zone: "Asia/Shanghai"
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) {
        SpringApplication.run(ExcelMain.class, args);
    }
}
