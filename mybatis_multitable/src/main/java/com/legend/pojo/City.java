package com.legend.pojo;

import javax.persistence.*;

/**
 * city
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/8/4
 */
@Table(name = "city")
public class City {

    /**
     * 城市编号
     */
    // 对应的是主键id
    @Id
    // 设置主键生成策略
    // IDENTITY：主键自增,数据库需要支持。
    // oracle的话就是序列(SEQUENCE)  AUTO：自动选择适合数据库的策略 TABLE：以表的形式存储生成主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 省份编号
     */
    @Column(name = "province_id")
    private Long provinceId;

    /**
     * 城市名称
     */
    @Column(name = "city_name")
    private String cityName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public City() {
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", provinceId=" + provinceId +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
