package com.legend.springbootredis.configuration;

import com.legend.springbootredis.interceptor.BrushInterfaceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 把Interceptor注册到springboot中
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/3/3
 */
//@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private BrushInterfaceInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}
