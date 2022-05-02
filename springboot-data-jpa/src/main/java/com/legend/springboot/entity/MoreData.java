package com.legend.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * 试用jpa注解配置对象关系映射
 */
@Entity
@Table(name = "t_more_data")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class MoreData {

    //这是一个主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增组件
    private Integer id;

    @Column
    private String img;

    @Column
    private int number;

    @Column
    private String shoplist;

    @Column
    private String image;
    @Column
    private String talk;
    private String more;
    private String morea;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getShoplist() {
        return shoplist;
    }

    public void setShoplist(String shoplist) {
        this.shoplist = shoplist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTalk() {
        return talk;
    }

    public void setTalk(String talk) {
        this.talk = talk;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public String getMorea() {
        return morea;
    }

    public void setMorea(String morea) {
        this.morea = morea;
    }

    public MoreData() {
    }

    public MoreData(String img, int number, String shoplist, String image, String talk, String more, String morea) {
        this.img = img;
        this.number = number;
        this.shoplist = shoplist;
        this.image = image;
        this.talk = talk;
        this.more = more;
        this.morea = morea;
    }
}
