package com.legend.springboot.controller;

import com.legend.springboot.entity.User;
import com.legend.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/user/{id}")
    public User query(@PathVariable(value = "id")Integer id){
        User user = userRepository.getOne(id);
        return user;
    }

    @GetMapping(value = "/user")
    public User query(User user){
        User save = userRepository.save(user);
        return save;
    }
}
