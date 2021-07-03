package com.legend.spring.config;

import com.legend.spring.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 属性赋值
 * 使用@PropertySource读取外部配置文件中的k/v保存到运行的环境变量中;加载完外部的配置文件以后${}取出配置文件的值
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
@PropertySource(value = {"classpath:/person.properties"})
@Configuration
public class MyConfigOfPropertyValue {

    @Bean
    public Person person() {
        return new Person();
    }
}
