package com.legend.springboot.config;

import com.legend.springboot.intercepter.LoginIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/12
 */
@Configuration
public class WebConfig {

    @Autowired
    private LoginIntercepter loginIntercepter;

    @Bean
    public WebMvcConfigurer configurer(){
        return new WebMvcConfigurer() {


            @Override
            public void addInterceptors(InterceptorRegistry registry) {

            }

        };
    }
}
