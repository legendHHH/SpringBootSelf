package com.redis.lock.service;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.MapUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用 Redisson 分布式锁，实现秒杀商品功能
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
@Service
public class KillItmsService {


    @Autowired
    private RedissonClient redissonClient;

    /**
     * 模拟多线程每次请求的用户id
     */
    private static List<Integer> userIds;

    /**
     * 初始秒杀商品的库存，实际情况可能存在redis
     */
    private static ConcurrentHashMap<Integer, AtomicInteger> killQuantityMap = new ConcurrentHashMap<>();

    /**
     * 记录秒杀成功的用户，实际情况可能存在redis
     */
    private static ConcurrentHashMap<Integer, Integer> killUserIdMaps = new ConcurrentHashMap<>();

    static {
        userIds = Lists.newArrayList(10001, 10002, 10003, 10004, 10005, 10006, 10007, 10008, 10009, 10010);
        killQuantityMap.put(1001, new AtomicInteger(6));
    }

    /**
     * 秒杀抢商品
     *
     * @param killId
     * @return
     */
    public Boolean killItem(Integer killId) {

        // 随机获取访问的用户，模拟不同用户请求
        int index = new Random().nextInt(9);
        Integer userId = userIds.get(index);

        boolean result = false;

        final String lockKey = String.valueOf(killId) + userId + "-RedissonLock";
        // 加锁
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // TODO: 2020/9/10 第一个参数为 30， 尝试获取锁的的最大等待时间为30s
            // TODO: 2020/9/10 第二个参数为 60，  上锁成功后60s后锁自动失效
            // 尝试获取锁（可重入锁,不会造成死锁）
            boolean lockFlag = lock.tryLock(30, 60, TimeUnit.SECONDS);
            if (lockFlag) {
                // 做幂等性处理
                if (MapUtils.isNotEmpty(killUserIdMaps) && killUserIdMaps.get(userId) != null) {
                    System.err.println("用户：" + userId + "---已抢到商品：" + killId + "，不可以重新领取");
                    return false;
                }

                /*
                 * ***************************************************************
                 *                          处理核心内容
                 * ***************************************************************
                 */
                AtomicInteger quantity = killQuantityMap.get(killId);
                if (quantity.get() > 0) {
                    quantity.decrementAndGet();
                    // TODO: 2020/9/10 killUserIdMaps 实际业务场景，秒杀抢到商品的用户可以存入redis缓存
                    killUserIdMaps.put(userId, killId);
                    // TODO: 2020/9/10 killQuantityMap 实际业务场景，读取数据库或者缓存的商品库存，判断是否被抢完了
                    killQuantityMap.put(killId, quantity);
                    System.out.println("用户：" + userId + "---抢到商品：" + killId);
                } else {
                    System.err.println("用户：" + userId + "---未抢到商品：" + killId);
                }
                result = true;
            } else {
                System.out.println("当前锁资源被占用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<未获取到锁");
            }
        } catch (Exception e) {
            System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.出现了错误");
        } finally {
            // 解锁
            lock.unlock();
        }
        return result;
    }

    public void init() {
        killQuantityMap.put(1001, new AtomicInteger(6));
        killUserIdMaps.clear();
    }
}
