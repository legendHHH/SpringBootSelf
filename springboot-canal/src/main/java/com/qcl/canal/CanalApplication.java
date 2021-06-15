package com.qcl.canal;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * canal测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/11
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableCanalClient
public class CanalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class, args);
    }
}
