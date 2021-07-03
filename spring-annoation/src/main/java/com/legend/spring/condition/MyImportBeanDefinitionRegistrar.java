package com.legend.spring.condition;

import com.legend.spring.bean.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ImportBeanDefinitionRegistrar导入组件
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     *
     * @param importingClassMetadata 当前类的注解信息
     * @param registry  BeanDefinition注册类
     *                  把所有需要添加到容器中的bean;调用BeanDefinitionRegistry.registerBeanDefinition手工注册进来
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //需要指定bean的全类名
        boolean definition = registry.containsBeanDefinition("com.legend.spring.bean.Red");
        boolean definition2 = registry.containsBeanDefinition("com.legend.spring.bean.Blue");
        if (definition && definition2) {
            //定义Bean信息
            RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
            //注册一个Bean,指定bean名
            registry.registerBeanDefinition("rainBow", beanDefinition);
        }

    }
}
