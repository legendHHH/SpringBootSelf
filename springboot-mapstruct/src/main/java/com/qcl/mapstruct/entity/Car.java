package com.qcl.mapstruct.entity;

import lombok.Data;

import java.util.Date;

/**
 * Car 是数据库映射类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/25
 */
@Data
public class Car {
    private String make;
    private Integer numberOfSeats;
    private Double price;
    private CarType carType;
    private Date addTime;
}
