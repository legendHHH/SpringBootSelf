package com.legend.spring;

import com.legend.spring.bean.Blue;
import com.legend.spring.bean.Person;
import com.legend.spring.config.MyConfig;
import com.legend.spring.config.MyConfig2;
import com.legend.spring.config.MyConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/2
 */
public class IOCLifeCycleTest {

    @SuppressWarnings("resources")
    @Test
    public void method() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfLifeCycle.class);
        System.out.println("容器创建完成");

        //多实例的时候是使用的时候才去创建对象
        //applicationContext.getBean("car");

        //关闭容器
        applicationContext.close();
    }
}
