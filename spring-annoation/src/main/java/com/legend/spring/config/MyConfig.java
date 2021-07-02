package com.legend.spring.config;

import com.legend.spring.bean.Person;
import com.legend.spring.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * 配置类=配置文件
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/2
 */
@Configuration
@ComponentScan(value = "com.legend.spring",
        //excludeFilters = Filter[],指定扫描的时候按照规则排除那些组件
        /*excludeFilters = {
                @Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
        })*/
        //includeFilters = Filter[],指定扫描的时候只需要包含那些组件
        includeFilters = {
                @Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class}),
                @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookDao.class})
        }, useDefaultFilters = false)

//FilterType.ANNOTATION,按照注解
//FilterType.ASSIGNABLE_TYPE,按照给定的类型
//FilterType.ASPECTJ,不常用
//FilterType.REGEX,按照正则表达式
//FilterType.CUSTOM,按照自定义
public class MyConfig {

    /**
     * 给容器中注册一个Bean;类型为返回值的类型,id默认是方法名作为id
     *
     * @return
     */
    @Bean
    public Person person() {
        return new Person(1, "44444");
    }
}
