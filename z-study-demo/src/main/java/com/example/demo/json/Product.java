package com.example.demo.json;

import lombok.AllArgsConstructor;

/**
 * Product
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/7/3
 */
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private Integer price;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }
}