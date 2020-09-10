package com.legend.springboot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * springboot的hello world案例
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2019/6/19
 */
@Controller
public class HelloWorldController {

    @ResponseBody
    @RequestMapping(value = "/hello")
    public String hello(){
        System.out.println("hello world");

        return "hello world";
    }
}
