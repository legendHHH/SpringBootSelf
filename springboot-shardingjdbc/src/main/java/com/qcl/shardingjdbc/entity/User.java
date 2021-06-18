package com.qcl.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户实体
 * TableName指定对应的表
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/19
 */
@Data
@TableName("t_user")
public class User {
    private Long userId;
    private String username;
    private String ustatus;
}
