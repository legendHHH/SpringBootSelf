package com.legend.spring.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * 使用Spring定义的FactoryBean
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
public class ColorFactoryBean implements FactoryBean<Color> {

    /**
     * 返回Color对象,这个对象会添加到容器中
     *
     * @return
     * @throws Exception
     */
    @Override
    public Color getObject() throws Exception {
        System.out.println("ColorFactoryBean....getObject");
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    /**
     * 是否单例
     * true：这个bean是单实例,在容器中保存一份
     * false：多实例,每次获取都会创建一个新的bean
     *
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
