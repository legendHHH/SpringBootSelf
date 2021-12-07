package com.qcl.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * SpringSecurity主启动类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/21
 */
@SpringBootApplication
//@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

}
