package com.example.demo.optional;

/**
 * 用户实体
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/21
 */
public class User {

    private String name;

    private String gender;

    private School school;

    public User() {
    }

    public User(String name) {
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", school=" + school +
                '}';
    }
}