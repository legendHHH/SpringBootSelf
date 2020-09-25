package com.redis.lock.util;


import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * Redisson单机版加锁解锁
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
public class RedissonUtil {

    public static void main(String[] args) {
        Config config = new Config();

        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        RedissonClient client = Redisson.create(config);

        RLock lock = client.getLock("lock1");

        try {
            lock.lock();
            System.out.println("业务处理");
        } finally {
            lock.unlock();
        }
    }
}
