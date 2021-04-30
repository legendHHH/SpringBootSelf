package com.legend.monitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/30
 */
@RequestMapping("/heap/test")
@RestController
public class TestController {

    public static final Map<String, Object> map = new ConcurrentHashMap<>();

    @GetMapping("/t")
    public String testHeapUsed() {
        for (int i = 0; i < 10000000; i++) {
            map.put(i + "", new Object());
        }
        return "ok";
    }
}