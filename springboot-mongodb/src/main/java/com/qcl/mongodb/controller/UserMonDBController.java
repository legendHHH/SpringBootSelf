package com.qcl.mongodb.controller;

import com.qcl.mongodb.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 主启动
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/29
 */
@RestController
public class UserMonDBController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("save-user")
    public String saveUser(@RequestBody User user){
        System.out.println("hello");
        User userSave = mongoTemplate.save(user);
        return userSave.toString();
    }

    @GetMapping("getdb")
    public String getDB(){
        System.out.println("hello");
        String dbName = mongoTemplate.getDb().getName();
        return dbName;
    }

    @GetMapping("getCollectionName")
    public String getCollectionName(){
        System.out.println("hello");
        String collectionName = mongoTemplate.getCollectionName(User.class);
        return collectionName;
    }

    @GetMapping("find-user")
    public List<User> findAllUser(){
        System.out.println("hello");
        List<User> userList = mongoTemplate.findAll(User.class);
        return userList;
    }
}
