package com.legend.spring;

import com.legend.spring.bean.Person;
import com.legend.spring.config.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 主启动类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/2
 */
public class MySpringMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);

        AnnotationConfigApplicationContext applicationContext2 = new AnnotationConfigApplicationContext(MyConfig.class);
        Person bean = applicationContext2.getBean(Person.class);
        System.out.println(bean);

        String[] names = applicationContext2.getBeanNamesForType(Person.class);
        for (String name:names) {
            System.out.println(name);
        }
    }
}
