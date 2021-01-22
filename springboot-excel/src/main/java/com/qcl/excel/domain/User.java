package com.qcl.excel.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2021/1/22
 */
@Data
public class User {
    private String name;
    private String address;
    private Date birth;

    public User() {
    }

    public User(String name, String address, Date birth) {
        this.name = name;
        this.address = address;
        this.birth = birth;
    }
}
