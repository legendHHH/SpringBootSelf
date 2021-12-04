package com.legend.springbootswagger.controller;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "TestController")
public class TestController {

    /**
     * http://localhost:8080/test
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ApiOperation(value = "测试2Mysql导出数据", notes = "mysql导出数据")
    public String test() {
        System.out.println("ddddd");
        return "1234";
    }

    /**
     * 第一种方式：自定义代码配置文档生成
     * 第二种方式：插件的形式生成文档
     *
     * http://localhost:8080/screw
     * screw-数据库表结构文档生成工具
     *
     * 支持的数据库
     * [✔️] MySQL、MariaDB、TIDB、Oracle、SqlServer、PostgreSQL、Cache DB
     */
    @RequestMapping(value = "/screw", method = RequestMethod.GET)
    @ApiOperation(value = "测试自定义代码配置文档生成Mysql文档数据", notes = "mysql")
    public void screwMySqlDoc() {
        //数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("123456");
        //设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        DataSource dataSource = new HikariDataSource(hikariConfig);
        //生成配置
        EngineConfig engineConfig = EngineConfig.builder()
                //生成文件路径
                .fileOutputDir("D:/logs")
                //打开目录
                .openOutputDir(true)
                //文件类型
                //.fileType(EngineFileType.WORD)
                .fileType(EngineFileType.MD)
                //生成模板实现
                .produceType(EngineTemplateType.freemarker).build();

        //忽略表
        List<String> ignoreTableName = new ArrayList<>();
        ignoreTableName.add("undo_log");
        ignoreTableName.add("lock_table");
        //忽略表前缀
        ArrayList<String> ignorePrefix = new ArrayList<>();
        ignorePrefix.add("t_");
        //忽略表后缀
        List<String> ignoreSuffix = new ArrayList<>();
        ignoreSuffix.add("_test");
        ProcessConfig processConfig = ProcessConfig.builder()
                //忽略表名
                .ignoreTableName(ignoreTableName)
                //忽略表前缀
                .ignoreTablePrefix(ignorePrefix)
                //忽略表后缀
                .ignoreTableSuffix(ignoreSuffix).build();
        //配置
        Configuration config = Configuration.builder()
                //版本
                .version("1.0.0")
                //描述
                .description("数据库设计文档生成")
                //数据源
                .dataSource(dataSource)
                //生成配置
                .engineConfig(engineConfig)
                //生成配置
                .produceConfig(processConfig).build();
        //执行生成
        new DocumentationExecute(config).execute();
    }


}
