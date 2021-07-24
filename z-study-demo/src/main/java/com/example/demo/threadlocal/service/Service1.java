package com.example.demo.threadlocal.service;

import com.example.demo.optional.User;
import com.example.demo.threadlocal.holder.UserContextHolder;

/**
 * service1
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/24
 */
public class Service1 {

    public void process() {
        //第一个service创建用户信息
        User user = new User("测试");
        UserContextHolder.holder.set(user);

        //第二第三获取共享的对象
        new Service2().process();
    }
}
