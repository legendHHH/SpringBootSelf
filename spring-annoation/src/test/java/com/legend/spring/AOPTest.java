package com.legend.spring;

import com.legend.spring.aop.MathCalculator;
import com.legend.spring.bean.Boss;
import com.legend.spring.bean.Car;
import com.legend.spring.bean.Color;
import com.legend.spring.config.MyConfigOfAOP;
import com.legend.spring.dao.BookDao;
import com.legend.spring.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/4
 */
public class AOPTest {

    @SuppressWarnings("resources")
    @Test
    public void method() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfAOP.class);
        System.out.println("容器创建完成");

        //不要自己手动创建 MathCalculator

        MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
        System.out.println("计算结果："+mathCalculator.div(1, 1));

        //关闭容器
        applicationContext.close();
    }
}
