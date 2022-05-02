package com.legend.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 使用jpa注解配置对象关系映射
 */
@Entity
@Table(name = "t_goods")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Goods {

    //这是一个主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增组件
    private Integer id;

    @Column(name = "name", length = 50)
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private String description;

    @Column
    private String images;
    @Column
    private String tags;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Goods() {
    }

    public Goods(String name, BigDecimal price, String description, String images, String tags) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.images = images;
        this.tags = tags;
    }
}
