package com.qcl.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 测试实体
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Test对象", description = "测试")
@TableName("t_test")
public class Test extends Model<Test> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * value与数据库主键列名一致，若实体类属性名与表主键列名一致可省略value
     */
    @TableId(type = IdType.AUTO)
    private Long tId;

    private String name;

    private String remark;


}
