package com.example.demo.string;

import lombok.Data;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/3/17
 */
@Data
public class Person {
    private Integer id;
    private String personName;

    public Person() {
    }

    public Person(String personName) {
        this.personName = personName;
    }
}
