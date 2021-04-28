package com.legend.dataway.springbootdataway;

import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 主启动类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/25
 */
@EnableHasor()
@EnableHasorWeb()
@SpringBootApplication
public class SpringbootDatawayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDatawayApplication.class, args);
    }
}
