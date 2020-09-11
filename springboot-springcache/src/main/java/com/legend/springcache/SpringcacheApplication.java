package com.legend.springcache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 开启springcache缓存@EnableCaching
 * 不然在 EmployeeMapper上面的CacheConfig配置不生效
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2019/12/3
 */
@SpringBootApplication
@MapperScan("com.legend.springcache.mapper")
@EnableCaching
public class SpringcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcacheApplication.class, args);
    }

}
