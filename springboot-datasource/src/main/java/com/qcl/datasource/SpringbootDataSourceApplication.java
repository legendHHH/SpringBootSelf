package com.qcl.datasource;

import com.qcl.datasource.config.DBConfig1;
import com.qcl.datasource.config.DBConfig2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 主启动类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
@SpringBootApplication
//或者在 DBConfig1 类上添加 @Component 注册进去Spring容器
@EnableConfigurationProperties({DBConfig1.class, DBConfig2.class})
public class SpringbootDataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDataSourceApplication.class, args);
    }

}
