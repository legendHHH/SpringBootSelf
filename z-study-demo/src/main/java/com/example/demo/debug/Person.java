package com.example.demo.debug;

import java.io.Serializable;

/**
 * 测试字段断点和字段监控
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/3
 */
public class Person implements Serializable {
    private Integer age;
    private String name;


    public Integer getAge() {
        return age;
    }

    public Person(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
