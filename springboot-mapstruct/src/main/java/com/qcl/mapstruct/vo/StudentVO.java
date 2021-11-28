package com.qcl.mapstruct.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StudentVO类
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
public class StudentVO {
    private String name;
    private Integer age;
    private String gender;
    private Double height;
    private String birthday;
}