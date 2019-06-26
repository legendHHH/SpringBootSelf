package com.legend.springboot;

import com.legend.springboot.bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootConfigApplicationTests {

    @Autowired
    private Person person;

    @Autowired
    private ApplicationContext ioc;

    @Test
    public void test(){
        System.out.println("测试......");
        boolean helloService = (boolean) ioc.getBean("helloService");
        System.out.println(""+helloService);
    }

    @Test
    public void contextLoads() {
        System.out.println("111"+person);
    }

}
