package com.qcl.excel.domain;

import lombok.Data;

/**
 * 用户实体
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/9/29
 */
@Data
public class Users {
    private Integer id;
    private String username;
    private String password;
}
