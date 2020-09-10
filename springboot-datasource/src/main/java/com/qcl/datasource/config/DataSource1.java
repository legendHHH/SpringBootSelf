package com.qcl.datasource.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 多数据源配置类
 * 下面要进行扫包，目的是标清楚为谁添加的数据源，这样对应的包里函数执行数据库操作的时候，就知道要执行的数据库账号
 * 密码，表，以及事务处理之类的。
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
//注册到springboot容器，相当于原来xml文件里的<beans>
@Configuration
@MapperScan(basePackages = {"com.qcl.datasource.mapper.test01"}, sqlSessionFactoryRef = "test1SqlSessionFactory")
public class DataSource1 {

    /**
     * 注入到这个容器
     * #primary是设置优先，因为有多个数据源，在没有明确指定用哪个的情况下，会用带有primary的，这个注解必须有一个数据源要添加
     *
     * @return
     */
    @Bean(name = "test1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test1")//表示取application.properties配置文件中的前缀
    @Primary
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 功能概述(test1 sql会话工厂)
     * //@Qualifier("xxx")的含义是告诉他使用哪个DataSource
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "test1SqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("test1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }


    /**
     * 功能概述(test1事务管理)
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "test1TransactionManager")//配置事务
    @Primary
    public DataSourceTransactionManager testTransactionManager(@Qualifier("test1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "test1SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}