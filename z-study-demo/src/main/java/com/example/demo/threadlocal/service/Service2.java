package com.example.demo.threadlocal.service;

import com.example.demo.optional.User;
import com.example.demo.threadlocal.holder.UserContextHolder;

/**
 * service2
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/24
 */
public class Service2 {

    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println(user + "   is Service2");

        new Service3().process();
    }
}
