package com.qcl.mybatisplus.dto;

import lombok.Data;

/**
 * 代码生成配置参数
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/4/3
 */
@Data
public class GeneratorParams {
    /**
     * 作者
     */
    private String author;
    /**
     * 数据库url
     */
    private String dbUrl;

    /**
     * 数据库用户名
     */
    private String dbUserName;

    /**
     * 数据库密码
     */
    private String dbPassword;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表前缀
     */
    private String tablePrefix;
}
