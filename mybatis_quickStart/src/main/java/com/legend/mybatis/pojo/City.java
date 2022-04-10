package com.legend.mybatis.pojo;


import javax.persistence.*;
import java.util.List;


/**
 * 城市
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/10
 */
@Table(name = "city")
public class City {

    /**
     * 城市编号
     */
    // 对应的是主键id
    @Id
    // 设置主键生成策略
    // IDENTITY：主键自增,数据库需要支持。oracle的话就是序列(SEQUENCE)  AUTO：自动选择适合数据库的策略 TABLE：以表的形式存储生成主键
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

    /**
     * 描述
     */
    private String description;

    @Column(name = "sales_num")
    private Integer salesNum;

    @Column(name = "eva_num")
    private Integer evaNum;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(Integer salesNum) {
        this.salesNum = salesNum;
    }

    public Integer getEvaNum() {
        return evaNum;
    }

    public void setEvaNum(Integer evaNum) {
        this.evaNum = evaNum;
    }

    public City(String cityName, String description) {
        this.cityName = cityName;
        this.description = description;
    }

    public City() {
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", provinceId=" + provinceId +
                ", cityName='" + cityName + '\'' +
                ", description='" + description + '\'' +
                ", salesNum=" + salesNum +
                ", evaNum=" + evaNum +
                '}';
    }
}
