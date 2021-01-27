package com.qcl.excel.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * t_user表
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/22
 */
@Data
@Entity //告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "t_user") //@Table来指定和哪个数据表对应;如果省略默认表名就是user;
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    private Date birth;
    private String sex;
    private String image;
    private String remark;

    public User() {
    }

    public User(String name, String address, Date birth) {
        this.name = name;
        this.address = address;
        this.birth = birth;
    }
}
