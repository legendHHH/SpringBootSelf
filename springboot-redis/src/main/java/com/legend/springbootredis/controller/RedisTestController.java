package com.legend.springbootredis.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

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
        System.out.println("script字符串："+redisScript.getScriptAsString());
        Boolean execute = redisTemplate.execute(redisScript, keys, "100");
        System.out.println(execute);
        return "abc";
    }
}
