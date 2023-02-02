package com.qcl.mapstruct.dto;

import lombok.Data;

import java.util.Date;

/**
 * CarDTO
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/25
 */
@Data
public class CarDTO {

    private String make;
    private Integer seatCount;
    private String type;
    private String price;
    private Date createTime;
}
