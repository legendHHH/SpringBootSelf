package com.legend.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自动装配
 * 默认加载ioc容器中的组件,容器启动会调用无参构造器创建对象,再进行初始化赋值等操作
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
@Component
public class Boss {

    private Car car;

    public Car getCar() {
        return car;
    }

    /**
     * 标注在方法上,Spring容器创建当前对象,就会调用方法,完成赋值;
     * 方法使用的参数,自定义类型的值从ioc容器中获取
     *
     * @param car
     */
    //@Autowired
    public void setCar(Car car) {
        this.car = car;
    }

    @Autowired
    public Boss(Car car) {
        System.out.println("boss......有参构造器");
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
