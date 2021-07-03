package com.legend.spring.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Linux环境条件
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
public class LinuxCondition implements Condition {

    /**
     * @param context  判断条件能使用的上下文(环境)
     * @param metadata 注释信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //是否Linux系统
        //1.获取到ioc使用的beanfactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        //2.获取类加载器
        ClassLoader classLoader = context.getClassLoader();
        //3.获取当前环境信息
        Environment environment = context.getEnvironment();

        //4.获取bean定义的注册类
        BeanDefinitionRegistry registry = context.getRegistry();
        //判断容器中的bean注册情况,也可以给容器中注册bean
        boolean person = registry.containsBeanDefinition("person");

        String property = environment.getProperty("os.name");
        if (property.contains("linux")) {
            System.out.println("matches linux");
            return true;
        }
        return false;
    }
}
