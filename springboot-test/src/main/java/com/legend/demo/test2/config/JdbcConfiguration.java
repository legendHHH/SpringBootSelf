package com.legend.demo.test2.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;


/**
 * @author chunlin.qi@hand-china.com
 *
 * 优化 提取到JdbcProperties类中,作为全局使用 不需要每次需要用就注入一下
 */
@Configuration //声明一个类是一个java配置类
//@PropertySource("classpath:jdbc-移到application.properties")  //读取资源文件
@EnableConfigurationProperties(JdbcProperties.class)  //启用jdbc的配置文件配置
public class JdbcConfiguration {
    //第一种方法
    /*@Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;*/


    //第二种方法
    //@Autowired
    //private JdbcProperties jdbcProperties;

    //第三种方式  @Autowired 注释
    /*public JdbcConfiguration(JdbcProperties jdbcProperties) {
        this.jdbcProperties = jdbcProperties;
    }*/


    /**
     * 把方法的返回值注入到Spring容器
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix="jdbc")  //第四种方法
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        //如果是全局的变量会在前面加this关键字   区分局部和全局
        /*dataSource.setDriverClassName(this.driverClassName);
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.userName);
        dataSource.setPassword(this.password);*/

        //第四种方法
        //替换成配置文件读取属性
        /*dataSource.setDriverClassName(this.jdbcProperties.getDriverClassName());
        dataSource.setUrl(this.jdbcProperties.getUrl());
        dataSource.setUsername(this.jdbcProperties.getUserName());
        dataSource.setPassword(this.jdbcProperties.getPassword());*/
        return dataSource;
    }
}
