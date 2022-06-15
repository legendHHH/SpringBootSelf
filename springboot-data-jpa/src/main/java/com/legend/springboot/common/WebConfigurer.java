package com.legend.springboot.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
class WebConfigurer extends WebMvcConfigurationSupport {

    //默认Linux
    String uploadPath = "data/";

    /**
     * 判断是不是windows
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            //如果是windows,就弄成windows的路径
            uploadPath = "D:/files/";
        }
        //最后把files下的目录映射到指定路径下
        registry.addResourceHandler("/files/**").addResourceLocations("file:///" + uploadPath);
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}