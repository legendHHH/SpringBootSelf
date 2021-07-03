package com.legend.spring.config;

import com.legend.spring.bean.Person;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;

/**
 * 配置类=配置文件
 *
 * @Scope 调整Bean的作用域
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
@Configuration
@ComponentScan(value = "com.legend.spring",
        //excludeFilters = Filter[],指定扫描的时候按照规则排除那些组件
        /*excludeFilters = {
                @Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
        })*/
        //includeFilters = Filter[],指定扫描的时候只需要包含那些组件
        includeFilters = {
                /*@Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class}),
                @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookDao.class})*/
                @Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
        }, useDefaultFilters = false)
//FilterType.ANNOTATION,按照注解
//FilterType.ASSIGNABLE_TYPE,按照给定的类型
//FilterType.ASPECTJ,不常用
//FilterType.REGEX,按照正则表达式
//FilterType.CUSTOM,按照自定义
public class MyConfig2 {

    /**
     * 给容器中注册一个Bean;类型为返回值的类型,id默认是方法名作为id
     * 默认是单实例
     * @Scope 调整Bean的作用域
     * <p>
     * * @see ConfigurableBeanFactory#SCOPE_PROTOTYPE prototype
     * * @see ConfigurableBeanFactory#SCOPE_SINGLETON singleton
     * * @see org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST  request
     * * @see org.springframework.web.context.WebApplicationContext#SCOPE_SESSION  session
     * <p>
     * prototype：多实例：IOC容器启动并不会去调用方法创建对象放在容器中每次获取的时候才会调用方法创建对象
     * singleton：单实例(默认的)：IOC容器启动会调用方法创建对象放到IOC容器中。以后每次获取就是直接从容器(map.get())中拿
     * request：同一次请求创建一个实例
     * session：同一个session创建一个实例
     *
     * @return
     */
    @Scope(value = "prototype")
    @Bean
    public Person person() {
        System.out.println("给容器中添加Person.....");
        return new Person(1, "44444");
    }
}
