package com.legend.demo.test2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hello2")
public class HelloController2 {

    @RequestMapping("/show2")
    public String helloW() {
        return "HAND Hello";
    }
}
