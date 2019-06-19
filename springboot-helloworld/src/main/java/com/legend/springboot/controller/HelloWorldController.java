package com.legend.springboot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    @ResponseBody
    @RequestMapping(value = "/hello")
    public String hello(){
        System.out.println("hello world");

        return "hello world";
    }
}
