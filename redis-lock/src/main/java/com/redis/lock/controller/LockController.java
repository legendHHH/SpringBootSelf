package com.redis.lock.controller;

import com.redis.lock.util.RedissonUtil;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis是单线程实例
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
@RestController
public class LockController {

    //private RedisTemplate<String,String> stringRedisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redisson;

    /**
     * 基于redisson实现高并发并能并发锁
     * http://localhost:1234/redisson-lock
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/redisson-lock")
    @ResponseBody
    public String redissonlock() throws InterruptedException {
        //jedis.setnx("lockKey",12345);

        String lockKey = "productid0001";

        //结果
        int result = 0;

        //保证释放锁是自己的线程标识
        String clientId = UUID.randomUUID().toString();

        //redissonLock
        RLock redissonLock = redisson.getLock(clientId);

        try {
            //Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "测试");
            //stringRedisTemplate.expire(lockKey, 10, TimeUnit.SECONDS);

            //引入了 redissonLock
            /*Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS);
            if (!result) {
                return "error";
            }*/

            //1/3 三分之一的时间
            redissonLock.lock(30, TimeUnit.SECONDS);

            int redisValueStock = Integer.parseInt(stringRedisTemplate.opsForValue().get(lockKey));
            if (redisValueStock > 0) {
                //jedis.get("lockKey");
                int realStock = redisValueStock - 1;
                //jedis.set("lockKey",12345);
                stringRedisTemplate.opsForValue().set(lockKey, String.valueOf(realStock));

                result = result + realStock;
                System.out.println("扣减成功,剩余库存:" + realStock + "");
            } else {
                System.out.println("扣减失败,库存不足");
            }

        } finally {
            //redissonLock 释放锁
            redissonLock.unlock();

            //解决高并发场景下代码锁永久失效的问题
            /*if (clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                //释放锁
                stringRedisTemplate.delete(lockKey);
            }*/

        }
        return "redis lock end!!!剩余库存：" + result;
    }


    /**
     * 并发锁
     * http://localhost:1234/lock?lockKeyFlag=productid0002
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/lock")
    @ResponseBody
    public String lock(String lockKeyFlag) {
        System.out.println("获取到的lockKeyFlag：" + lockKeyFlag);

        //结果
        int retStock = 0;

        //保证释放锁是自己的线程标识
        String clientId = UUID.randomUUID().toString();
        System.out.println("生成的clientId：" + clientId);

        try {
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(clientId, clientId,10,TimeUnit.MINUTES);

            if (!result) {
                return "[ERROR]当前访问人数过多,服务繁忙请稍后再试！！！！";
            }
            int redisValueStock = Integer.parseInt(stringRedisTemplate.opsForValue().get(lockKeyFlag));
            if (redisValueStock > 0) {
                int realStock = redisValueStock - 1;
                stringRedisTemplate.opsForValue().set(lockKeyFlag, String.valueOf(realStock));

                //返回前端显示
                retStock = retStock + realStock;
                System.out.println("扣减成功,剩余库存:" + realStock + "");
            } else {
                System.out.println("扣减失败,库存不足");
            }
        } finally {
            //解决高并发场景下代码锁永久失效的问题
            String id = stringRedisTemplate.opsForValue().get(clientId);
            if (clientId.equals(id)) {
                System.out.println("获取到的clientId：" + id);
                //释放锁
                stringRedisTemplate.delete(clientId);
            }
        }
        return "redis lock end!!!剩余库存：" + retStock;
    }


    /**
     * 给Redis设置一个key
     * http://localhost:1234/setRedisKey?lockKey=productid0002
     *
     * @param lockKey
     * @return
     */
    @GetMapping("/setRedisKey")
    @ResponseBody
    public String setRedisKey(String lockKey) {
        if (StringUtils.isEmpty(lockKey)) {
            lockKey = "productid0001";
        }
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "100");
        return result.toString();
    }

    /**
     * 获取RedissonConfig配置信息
     * http://localhost:1234/getRedissonConfig
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/getRedissonConfig")
    @ResponseBody
    public String redissonConfig() throws Exception {
        System.out.println("hello");
        //来检测是否配置成功;
        return redisson.getConfig().toJSON();
    }
}
