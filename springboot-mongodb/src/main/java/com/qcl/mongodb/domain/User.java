package com.qcl.mongodb.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * 实体类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/28
 */
@Document(collection = "user")  //存入的表名称
public class User {

    /**
     * 标志为主键
     */
    @Id
    private Integer id;
    private String name;
    private BigDecimal height;
    private BigDecimal weight;

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

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public User() {
    }

    public User(Integer id, String name, BigDecimal height, BigDecimal weight) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
