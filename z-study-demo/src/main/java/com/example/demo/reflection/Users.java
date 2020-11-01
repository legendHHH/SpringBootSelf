package com.example.demo.reflection;

import com.example.demo.optional.School;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户实体
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/01
 */
public class Users {

    private Integer id;
    private String name;

    private String gender;

    private School school;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    public Users() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", school=" + school +
                '}';
    }
}