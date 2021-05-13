package com.qcl.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 用户实体类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/13
 */
@Data
@ToString
public class User {

    /**
     * 主键策略
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 用户名支持驼峰
     */
    private String userName;
    private Integer age;
    private String email;

    /**
     * 创建时间(测试自动填充属性)
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后更新时间(测试自动填充属性)
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateTime;

    /**
     * mp实现乐观锁
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
}
