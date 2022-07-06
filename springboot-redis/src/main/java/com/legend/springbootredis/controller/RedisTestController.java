package com.legend.springbootredis.controller;

import com.alibaba.fastjson.JSONObject;
import com.legend.springbootredis.RedisUtil;
import com.legend.springbootredis.entity.User;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private DefaultRedisScript<Boolean> redisScript;

    /**
     * 测试key value
     * http://localhost:8081/redisTest/set/kp/999
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping(value = "set/{key}/{value}", method = RequestMethod.GET)
    public String testSet(@PathVariable String key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.opsForSet().add("10_exportTask", value);
        return key + ":" + value;
    }

    /**
     * 获取key的值
     * http://localhost:8081/redisTest/get/kp
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "get/{key}", method = RequestMethod.GET)
    public String testGet(@PathVariable String key) {
        String value = redisTemplate.opsForValue().get(key);
        return "根据key获取的value是" + value;
    }

    /**
     * 获取所有key的值到本地redis数据库
     * http://localhost:8081/redisTest/syncAllKeyValue
     *
     * @return
     */
    @RequestMapping(value = "syncAllKeyValue", method = RequestMethod.GET)
    public String syncAllKeyValue() {
        Set<String> keys = redisTemplate.keys("*");
        //    NONE("none"),
        //    STRING("string"),
        //    LIST("list"),
        //    SET("set"),
        //    ZSET("zset"),
        //    HASH("hash");
        for (String key : keys) {
            DataType type = redisTemplate.type(key);

            switch (type) {
                case STRING:
                    String value = redisTemplate.opsForValue().get(key);
                    RedisUtil.set(key, value);
                    break;
                case SET:
                    System.out.println("set");
                    break;
                case ZSET:
                    System.out.println("ZSET");
                    break;
                case HASH:
                    System.out.println("ZSET");
                    break;
                default:
                    break;
            }
        }
        return "success";
    }

    /**
     * 测试设置xxxx
     *
     * @param request
     * @param value
     * @return
     */
    @RequestMapping(value = "set/{value}", method = RequestMethod.GET)
    @ResponseBody
    public String test(HttpServletRequest request, @PathVariable String value) {
        Thread thread = Thread.currentThread();
        System.out.println("当前请求：" + request.getSession().getId());
        System.out.println("当前线程：" + thread);
        String key = thread.getId() + "";
        //redisTemplate.opsForValue().set(key, value);
        Thread.currentThread().getPriority();
        return key + ":" + value;
    }

    /**
     * 测试Lua操作redis
     *
     * @return
     */
    @GetMapping("/lua")
    @ResponseBody
    public String testLua() {
        List<String> keys = Arrays.asList("testLua", "hello lua");
        System.out.println("script字符串：" + redisScript.getScriptAsString());
        Boolean execute = redisTemplate.execute(redisScript, keys, "100");
        System.out.println(execute);
        return "abc";
    }

    /**
     * 测试接口幂等
     *
     * @return
     */
    @PostMapping("/mideng")
    @ResponseBody
    public String miDeng(HttpServletRequest request) {
        String requestNo = request.getParameter("requestNo");
        String state = request.getParameter("state");
        //判断redis的值是否存在
        String key = "test_" + requestNo + "_" + state;

        if (redisTemplate.hasKey(key)) {
            String value = redisTemplate.opsForValue().get(key);

            if (StringUtils.isNotEmpty(value) && !"".equals(value) && !value.contains("正在处理中...")) {
                System.out.println("从缓存中获取......");
                User user = JSONObject.parseObject(value, User.class);
                user.setPassword("q12121212");
                user.setName("从缓存中拿到name");
                return JSONObject.toJSONString(user);
            }
            System.out.println("正在处理中.....");
            return "正在处理中.....";
        }
        redisTemplate.opsForValue().set(key, "正在处理中.....", 20);

        //TODO 业务处理
        User user = new User(1, "666");
        try {
            Thread.sleep(1000);

            System.out.println("从业务处理中获取......");
            redisTemplate.opsForValue().set(key, JSONObject.toJSONString(user), 20);
        } catch (InterruptedException e) {
            System.out.println("出现异常.....");
            e.printStackTrace();
            redisTemplate.delete(key);
        }

        return JSONObject.toJSONString(user);
    }


    /**
     * 测试接口幂等
     *
     * @return
     */
    @PostMapping("/midengPro")
    @ResponseBody
    public String miDengPro(HttpServletRequest request) {
        String requestNo = request.getParameter("requestNo");
        String state = request.getParameter("state");
        //判断redis的值是否存在
        String key = "test_" + requestNo + "_" + state;

        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key + "_7", "正在处理中....");
        System.out.println(flag);
        if (!flag) {
            String value = redisTemplate.opsForValue().get(key);

            if (StringUtils.isNotEmpty(value) && !"".equals(value) && !value.contains("正在处理中...")) {
                System.out.println("从缓存中获取......");
                User user = JSONObject.parseObject(value, User.class);
                user.setPassword("121212");
                user.setName("拿到name");
                return JSONObject.toJSONString(user);
            }
            System.out.println("正在处理中.....");
            return "正在处理中.....";
        }

        //TODO 业务处理
        User user = new User(12, "1234");
        try {
            Thread.sleep(1000);

            System.out.println("从业务处理中获取......");
            redisTemplate.opsForValue().set(key, JSONObject.toJSONString(user), 20);
        } catch (InterruptedException e) {
            System.out.println("出现异常.....");
            e.printStackTrace();
            redisTemplate.delete(key);
        }

        return JSONObject.toJSONString(user);
    }
}
