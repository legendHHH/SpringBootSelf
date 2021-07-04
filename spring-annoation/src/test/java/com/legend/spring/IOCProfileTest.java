package com.legend.spring;

import com.legend.spring.config.MyConfigOfProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/4
 */
public class IOCProfileTest {

    @SuppressWarnings("resources")
    @Test
    public void method2() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        System.out.println("容器创建完成");

        //创建一个applicationContext
        //设置需要激活的环境
        applicationContext.getEnvironment().setActiveProfiles("test","dev");

        //注册主配置类
        applicationContext.register(MyConfigOfProfile.class);

        //启动刷新容器
        applicationContext.refresh();

        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(DataSource.class);
        for (String name: beanDefinitionNames) {
            System.out.println(name);
        }

        //关闭容器
        applicationContext.close();
    }

    @SuppressWarnings("resources")
    @Test
    public void method() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfProfile.class);
        System.out.println("容器创建完成");

        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(DataSource.class);
        for (String name: beanDefinitionNames) {
            System.out.println(name);
        }

        //关闭容器
        applicationContext.close();
    }
}
