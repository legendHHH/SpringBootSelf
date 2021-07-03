package com.legend.spring.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 生命周期2
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
public class Cat implements InitializingBean, DisposableBean {

    public Cat() {
        System.out.println("Cat Constructor.......");
    }

    @Override
    public void destroy() {
        System.out.println("Cat destroyMethod.......");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("Cat afterPropertiesSet.......");
    }
}
