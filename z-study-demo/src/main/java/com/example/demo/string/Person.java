package com.example.demo.string;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Person类不能直接用@Data注解,这个注解默认帮我们重写了hashCode,如果我们需要比较两个对象是否相等会返回true
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/3/17
 */
@Getter
@Setter
@AllArgsConstructor
public class Person {
    private Integer id;
    private String personName;

    public Person() {
    }

    public Person(String personName) {
        this.personName = personName;
    }

    /**
     * 如果名字相同就是同一个对象,可以使用下面的方法
     *
     * @param o
     * @return
     */
    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personName, person.personName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personName);
    }*/
}
