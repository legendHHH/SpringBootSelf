package com.example.demo.json;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Product
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/7/3
 */
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private Integer price;
    private Date lastUpdateTime;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}