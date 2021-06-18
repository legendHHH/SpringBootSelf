package com.qcl.shardingjdbc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 公共表实体
 * TableName指定对应的表
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/18
 */
@Data
@TableName("t_udict")
public class Udict {
    @TableId
    private Long dictid;
    private String uvalues;
    private String ustatus;
    private String name;
}
