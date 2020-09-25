package com.redis.lock.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
@Configuration
public class RedisConfig {

    @Value("${redis.config.host}")
    private String address;

    @Bean
    public RedissonClient getRedissonClient() {
        // 创建 Config
        Config config = new Config();
        // 设置为单节点redis
        config.useSingleServer().setAddress(this.address).setDatabase(3);
        // 通过Redisson创建client实例
        return Redisson.create(config);
    }
}
