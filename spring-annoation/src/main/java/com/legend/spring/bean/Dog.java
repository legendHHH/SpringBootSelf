package com.legend.spring.bean;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 生命周期3
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
@Component
public class Dog implements ApplicationContextAware {

    /**
     * 在容器中保存起来
     */
    private ApplicationContext applicationContext;

    public Dog() {
        System.out.println("Dog Constructor.......");
    }

    /**
     * 对象创建并赋值之后调用
     *
     * //这个处理器处理的
     * org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor
     *
     * @throws Exception
     */
    @PostConstruct
    public void init() {
        System.out.println("Dog @PostConstruct.......");
    }

    /**
     * 容器移除对象之前
     *
     * @throws Exception
     */
    @PreDestroy
    public void detory() {
        System.out.println("Dog @PreDestroy.......");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //ApplicationContextAwareProcess来实现的(org.springframework.context.support.ApplicationContextAwareProcessor)
        this.applicationContext = applicationContext;
    }
}
