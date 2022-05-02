package com.legend.springboot.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * 试用jpa注解配置对象关系映射
 */
@Entity
@Table(name = "t_user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class User {

    //这是一个主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增组件
    private Integer id;

    @Column(name = "user_name", length = 50)
    private String userName;

    @Column
    private String email;

    @Column
    private String phone;
    private String password;
    private String sex;
    private String birthday;
    private String detail;

    public User() {
    }

    public User(String userName, String email, String phone, String password, String sex, String birthday, String detail) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.sex = sex;
        this.birthday = birthday;
        this.detail = detail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
