package com.legend.springbootredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * redis测试接口
 *
 * @author legend
 */
@RestController
@RequestMapping("/redisTest")
public class RedisTestController {
    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 测试key value
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping(value = "set/{key}/{value}", method = RequestMethod.GET)
    public String testSet(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value);
        return key + ":" + value;
    }

    @RequestMapping(value = "get/{key}", method = RequestMethod.GET)
    public String testGet(@PathVariable String key) {
        String value = redisTemplate.opsForValue().get(key);
        return "根据key获取的value是" + value;
    }
}
