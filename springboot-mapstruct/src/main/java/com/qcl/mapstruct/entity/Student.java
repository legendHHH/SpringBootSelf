package com.qcl.mapstruct.entity;

import com.qcl.mapstruct.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Student是数据库映射类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/11/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private String name;
    private Integer age;
    private GenderEnum gender;
    private Double height;
    private Date birthday;
    private String address;
}