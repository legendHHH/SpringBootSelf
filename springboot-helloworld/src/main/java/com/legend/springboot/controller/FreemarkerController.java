package com.legend.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用ftl模板测试接口
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
@Controller
public class FreemarkerController {


    /**
     * ftl获取list方式
     * http://localhost:8089/index2
     *
     * @return
     */
    @GetMapping("/index2")
    public String method2() {
        Map<String, Object> map = new HashMap<>(10);
        System.out.println("hello");
        List<String> list = Arrays.asList("11","12","13","14","10","15","16","17");
        map.put("name", "hello");
        map.put("sex", "0");
        map.put("userList", list);
        //TODO 无法转发到静态资源文件夹下面
        return "index2";
    }

    /**
     * 跳转index的ftl文件
     * http://localhost:8089/index
     *
     * @param map
     * @return
     */
    @GetMapping("/index")
    public String method(Map<String, String> map) {
        System.out.println("hello");
        map.put("name", "hello world");
        return "index";
    }
}
