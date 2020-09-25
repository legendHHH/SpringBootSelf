package com.qcl.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Book实体对象
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("book")
public class Book extends Model<Book> {

    private int id;

    private String name;

    private int count;
}