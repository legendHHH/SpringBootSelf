package com.legend.spring;

import com.legend.spring.bean.Blue;
import com.legend.spring.bean.Person;
import com.legend.spring.config.MyConfig;
import com.legend.spring.config.MyConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/2
 */
public class IOCTest {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig2.class);

    @SuppressWarnings("resources")
    @Test
    public void method5() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println("method5：" + name);
        }
        Blue bean = applicationContext.getBean(Blue.class);

        //工厂Bean获取的是调用getObject创建的对象
        Object bean1 = applicationContext.getBean("colorFactoryBean");
        Object bean2 = applicationContext.getBean("colorFactoryBean");
        Object bean3 = applicationContext.getBean("&colorFactoryBean");
        System.out.println("bean1的类型:" + bean1.getClass());
        System.out.println("bean2的类型:" + bean2.getClass());
        System.out.println("bean3的类型:" + bean3.getClass());
        System.out.println("bean的比较：" + (bean1 == bean2));
    }

    @SuppressWarnings("resources")
    @Test
    public void method4() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println("method4：" + name);
        }

        Blue bean = applicationContext.getBean(Blue.class);
        System.out.println("bean:" + bean);
    }

    @SuppressWarnings("resources")
    @Test
    public void method3() {
        String[] beanNamesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String name : beanNamesForType) {
            System.out.println("method3：" + name);
        }

        //测试Linux 可以修改VM Option ： -Dos.name=linux
        //动态获取环境变量的值：Windows 10
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("os.name");
        System.out.println(property);
    }

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
