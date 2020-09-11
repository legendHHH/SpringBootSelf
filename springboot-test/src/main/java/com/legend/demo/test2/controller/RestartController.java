package com.legend.demo.test2.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@RequestMapping("/rest")
public class RestartController {

    @Value("${name}")
    private String name;

    @RequestMapping("/name")
    public String name() {
        System.out.println(name);
        return "HANDHello" + name;
    }
}
