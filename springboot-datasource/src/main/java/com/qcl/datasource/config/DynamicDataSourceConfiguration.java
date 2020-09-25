package com.qcl.datasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.qcl.datasource.bean.AreaKeyId;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态路由数据源管理
 * <p>
 * 用户登陆,获取用户的地区,判断是028还是010根据这个获取对应的数据源进行操作对应的数据库
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/11
 */
//@Configuration
@Deprecated
public class DynamicDataSourceConfiguration {


    /**
     * 获取到数据源相关配置
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "multiple.datasource.beijing")
    public DataSource beijing010() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "multiple.datasource.chengdu")
    public DataSource chengdu028() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 核心动态数据源配置
     */
    @Bean
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<Object, Object>(2);
        //设置默认数据源
        dataSource.setDefaultTargetDataSource(beijing010());
        //配需数据原
        dataSourceMap.put(AreaKeyId.BEIJING, beijing010());
        dataSourceMap.put(AreaKeyId.CHENGDU, chengdu028());
        dataSource.setTargetDataSources(dataSourceMap);
        return dataSource;
    }

    /**
     * SqlSessionFactory
     *
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        //此处设置为了解决找不到mapper文件的问题
        //sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }


    /**
     * SqlSessionTemplate
     *
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    /**
     * 事务管理
     *
     * @return
     * @throws Exception
     */
    @Bean
    public PlatformTransactionManager platformTransactionManager() throws Exception {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
