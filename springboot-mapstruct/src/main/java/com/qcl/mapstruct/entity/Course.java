package com.qcl.mapstruct.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Course多对象
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/12/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private String courseName;
    private Integer sortNo;
    private Long id;

}