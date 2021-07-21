package com.legend.demo.test2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String helloW() throws InterruptedException {
        //测试超时
        Thread.sleep(10000);
        System.out.println(dataSource);
        return "HAND Hello";
    }

    /**
     * http://localhost:9909/hello/show2?pageNo=2&pageSize=20
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/show2")
    public String helloW2(@RequestParam(required = false, defaultValue = "1", value = "pageNo") int page,
                          @RequestParam(required = false, defaultValue = "10", value = "pageSize") int limit) {
        System.out.println("page："+ page + "pageSize：" + limit);
        return "page："+ page + "pageSize：" + limit;
    }
}
