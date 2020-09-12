package com.redis.lock.config;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * RedissonClientConfig配置
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
//@Component
@Deprecated
public class RedissonClientConfig {

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() throws IOException {
        String[] profiles = env.getActiveProfiles();
        String profile = "";
        if(profiles.length > 0) {
            profile = "-" + profiles[0];
        }
        return Redisson.create(Config.fromYAML(new ClassPathResource("redisson" + profile + ".yml").getInputStream()));
    }
    
}