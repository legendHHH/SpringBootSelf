package com.legend.demo.test2.controller;

import com.legend.demo.test2.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hello2")
public class HelloController2 {

    @RequestMapping("/show2")
    public String helloW() {
        return "HAND Hello";
    }


    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String helloUser(User user) {
        System.out.println(user.toString());
        return "HAND Hello" + user;
    }
}
