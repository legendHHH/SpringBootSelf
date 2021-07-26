package com.example.demo.threadlocal.holder;

import com.example.demo.optional.User;

/**
 * 用户的持有者保存用户信息
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/26
 */
public class UserContextHolder {
    public static ThreadLocal<User> holder = new ThreadLocal<User>();
}