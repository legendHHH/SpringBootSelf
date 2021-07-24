package com.example.demo.threadlocal;

/**
 * 实体类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/24
 */
public class User {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
