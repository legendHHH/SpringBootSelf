package com.legend.retrofit.entity;

/**
 * 实体类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/2
 */
public class Person {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Person(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
