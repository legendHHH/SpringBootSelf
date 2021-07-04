package com.legend.spring.config;

import com.legend.spring.bean.Yellow;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;

/**
 * 配置类=配置文件
 *
 * Profile：
 *      Spring为我们提供可以根据当前环境，动态的激活和切换一系列组件的功能
 *
 * 开发环境、测试环境、生产环境
 * 数据源 A、B、C
 *
 * @Profile("dev")：指定组件在哪个环境下才能被注册到容器中,不指定,任何环境下都能注册这个组件
 *   1.加了环境标识的bean，只有这个环境被激活的时候才能注册到容器中。默认是default
 *   2.写在配置类上,只有是指定的环境的时候,整合配置类里面的所有配置才能开始生效
 *   3.没有标注环境的bean，在任何环境下都是加载的
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/4
 */
//@Profile("test")
@PropertySource("classpath:/dbconfig.properties")
@Configuration
public class MyConfigOfProfile implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String user;

    private String driverClass;

    private StringValueResolver valueResolver;

    /**
     * 使用动态命令行参数：在虚拟机参数设置位置加载 -Dspring.profiles.active=test
     *
     * @return
     */
    @Profile("test")
    @Bean
    public Yellow yellow(){
        return new Yellow();
    }

    //@Profile("default")
    @Profile("test")
    @Bean("dataSourceTest")
    public DataSource dataSourceTest(@Value("${db.password}") String password) throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUser(user);
        dataSource.setPassword(password);


        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    @Profile("dev")
    @Bean("dataSourceDev")
    public DataSource dataSourceDev() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/db0");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Profile("prod")
    @Bean("dataSourceProd")
    public DataSource dataSourceProd() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/db1");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.valueResolver = resolver;
        driverClass = valueResolver.resolveStringValue("${db.driverClass}");
    }
}
