package com.legend.service.controller;

import com.legend.service.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("consumer/user")
public class UserController {

    /**
     * 远程调用
     */
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public User queryUserById(@RequestParam("id") Long id){
        return this.restTemplate.getForObject("http://localhost:8099/user/"+id, User.class);
    }
}
