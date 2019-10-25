package com.legend.demo.test2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@RequestMapping("/hello")
public class HelloController {

    //@Autowired
    private DataSource dataSource;

    //通过构造方法注入  @Autowired
    public HelloController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @RequestMapping("/show")
    public String helloW() {
        System.out.println(dataSource);
        return "HAND Hello";
    }
}
