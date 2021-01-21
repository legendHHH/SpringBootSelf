package com.legend.springcache.controller;

import com.legend.springcache.entity.UserInfo;
import com.legend.springcache.service.UserInfo2Service;
import com.legend.springcache.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/20
 */
@RestController
@RequestMapping
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfo2Service userInfo2Service;


    @GetMapping("/userInfo/{id}")
    public Object getUserInfo(@PathVariable Integer id) {
        UserInfo userInfo = userInfoService.getByName(id);
        if (userInfo == null) {
            return "没有该用户";
        }
        return userInfo;
    }

    @PostMapping("/userInfo")
    public Object createUserInfo(@RequestBody UserInfo userInfo) {
        userInfoService.addUserInfo(userInfo);
        return "SUCCESS";
    }

    @PutMapping("/userInfo")
    public Object updateUserInfo(@RequestBody UserInfo userInfo) {
        UserInfo newUserInfo = userInfoService.updateUserInfo(userInfo);
        if (newUserInfo == null) {
            return "不存在该用户";
        }
        return newUserInfo;
    }

    @DeleteMapping("/userInfo/{id}")
    public Object deleteUserInfo(@PathVariable Integer id) {
        userInfoService.deleteById(id);
        return "SUCCESS";
    }





    @GetMapping("/userInfo2/{id}")
    public Object getUserInfo2(@PathVariable Integer id) {
        UserInfo userInfo = userInfo2Service.getByName(id);
        if (userInfo == null) {
            return "没有该用户";
        }
        return userInfo;
    }

    @PostMapping("/userInfo2")
    public Object createUserInfo2(@RequestBody UserInfo userInfo) {
        userInfo2Service.addUserInfo(userInfo);
        return "SUCCESS";
    }

    @PutMapping("/userInfo2")
    public Object updateUserInfo2(@RequestBody UserInfo userInfo) {
        UserInfo newUserInfo = userInfo2Service.updateUserInfo(userInfo);
        if (newUserInfo == null) {
            return "不存在该用户";
        }
        return newUserInfo;
    }

    @DeleteMapping("/userInfo2/{id}")
    public Object deleteUserInfo2(@PathVariable Integer id) {
        userInfo2Service.deleteById(id);
        return "SUCCESS";
    }
}
