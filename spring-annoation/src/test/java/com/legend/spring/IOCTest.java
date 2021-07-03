package com.legend.spring;

import com.legend.spring.bean.Person;
import com.legend.spring.config.MyConfig;
import com.legend.spring.config.MyConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/2
 */
public class IOCTest {


    @SuppressWarnings("resources")
    @Test
    public void method2() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig2.class);
        System.out.println("IOC容器创建完成.....");
        Person person = (Person) applicationContext.getBean("person");
        Person person2 = (Person) applicationContext.getBean("person");
        System.out.println(person);
        System.out.println(person == person2);
    }

    @SuppressWarnings("resources")
    @Test
    public void method() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }
}
