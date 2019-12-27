package com.legend.springboot.starter.config;

import com.legend.springboot.starter.service.TestService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "legend.starter",name = "enabled",havingValue = "true")
public class TestServiceAutoConfiguration {
    @Bean
    public TestService testService(){
        return new TestService();
    }
}
