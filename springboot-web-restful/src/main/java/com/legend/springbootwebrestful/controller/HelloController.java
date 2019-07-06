package com.legend.springbootwebrestful.controller;

import com.legend.springbootwebrestful.exception.UserNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

@Controller
public class HelloController {


    /*@RequestMapping({"/","/index.html"})
    public String index(){
       return "index";
    }*/

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    //测试thymeleaf渲染
    @RequestMapping("/success")
    public String success(Map<String,Object> map){
        map.put("hello","<h1>你好!!<h1>");
        map.put("users",Arrays.asList("张三","李四","wangwu"));
        return "success";
    }



    @ResponseBody
    @RequestMapping("/helloError")
    public String exception(@RequestParam("user") String user){
        if (user.equals("aaa")){
            throw new UserNotExistException();
        }
        return "success";
    }
}
