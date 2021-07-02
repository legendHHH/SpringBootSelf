package com.legend.spring.config;

import com.legend.spring.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/2
 */
@Configuration
public class MyConfig {

    @Bean
    public Person person() {
        return new Person(1, "44444");
    }
}
