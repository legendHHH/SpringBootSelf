package com.legend.spring;

import com.legend.spring.bean.Boss;
import com.legend.spring.bean.Car;
import com.legend.spring.config.MyConfigOfAutowired;
import com.legend.spring.dao.BookDao;
import com.legend.spring.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
public class IOCAutoWireTest {

    @SuppressWarnings("resources")
    @Test
    public void method() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigOfAutowired.class);
        System.out.println("容器创建完成");

        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService);

        BookDao bookDao = (BookDao) applicationContext.getBean("bookDao");
        System.out.println(bookDao);

        Boss boss = applicationContext.getBean(Boss.class);
        System.out.println(boss);

        Car car = applicationContext.getBean(Car.class);
        System.out.println(car);

        //关闭容器
        applicationContext.close();
    }
}
