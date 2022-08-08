package com.qcl.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/8/7
 */
@Data // getter setter toString
@NoArgsConstructor //生成无参构造
@AllArgsConstructor // 生成全参构造
@TableName("user")
public class User {

    private Long id;
    private String userName;
    private Integer age;
    private String email;


}
