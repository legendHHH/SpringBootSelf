package com.legend.spring;

import com.legend.spring.bean.Person;
import com.legend.spring.config.MyConfigOfPropertyValue;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
public class IOCPropertyValueTest {

    @SuppressWarnings("resources")
    @Test
    public void method() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfPropertyValue.class);
        System.out.println("容器创建完成");

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }

        //根据id
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("person.nickname");
        System.out.println("环境变量：" + property);

        //关闭容器
        applicationContext.close();
    }
}
