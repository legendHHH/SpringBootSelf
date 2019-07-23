package com.legend.springboot.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * 试用jpa注解配置对象关系映射
 */
@Entity //告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "tbl_user") //@Table来指定和哪个数据表对应;如果省略默认表名就是user;

//异常com.fasterxml.jackson.databind.exc.InvalidDefinitionException:
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User {

    //这是一个主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增组件
    private Integer id;

    @Column(name = "last_name",length = 50) //这是和数据表对应的一个列
    private String lastName;

    @Column //省略默认列名就是属性名
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
