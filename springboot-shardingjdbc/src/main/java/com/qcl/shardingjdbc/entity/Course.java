package com.qcl.shardingjdbc.entity;

import lombok.Data;

/**
 * 课程表
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/16
 */
@Data
public class Course {
    private Long cid;
    private String cname;
    private String cstatus;
    private Long userId;
}
