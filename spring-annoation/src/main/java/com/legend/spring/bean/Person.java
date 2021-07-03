package com.legend.spring.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * Person
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/2
 */
public class Person {

    /**
     * 使用@Value赋值：
     *      1.基本数值
     *      2.可以写SpEL;#{}
     *      3.可以写${};取出配置文件中的值(在运行环境变量里面的值)
     */
    @Value("#{20-2}")
    private Integer id;

    @Value("张三")
    private String name;

    @Value("${person.nickname}")
    private String nickName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person() {
    }

    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
