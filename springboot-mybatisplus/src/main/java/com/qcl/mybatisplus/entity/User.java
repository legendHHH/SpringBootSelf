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

    /**
     * TableField注解select = false 表示大字段不加入查询字段，查询的时候不返回该字段的值
     */
    @TableField(select = false)
    private Integer age;

    /**
     * TableField注解value 解决字段名不一致
     */
    @TableField(value = "email")
    private String mail;

    /**
     * TableField注解exist = false 解决字段在数据库表中不存在情况
     */
    @TableField(exist = false)
    private String address;

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

    /**
     * 逻辑删除标识位
     * TableLogic 配置逻辑删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
