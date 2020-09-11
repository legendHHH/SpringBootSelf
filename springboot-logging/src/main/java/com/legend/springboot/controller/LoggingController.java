package com.legend.springboot.controller;

import com.legend.springboot.annotation.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志的AOP切面类处理
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/9/11
 */
@RestController
public class LoggingController {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(LoggingController.class);


    /**
     * 全局异常拦截处理(空指针异常)
     * <p>
     * http://localhost:8080/exception2
     *
     * @param name
     * @return
     */
    @GetMapping("/exception2")
    @ResponseBody
    public String exception2(String name) {
        logger.info("hello");
        String str = null;
        str.substring(0, 1);
        return "log:" + name;
    }

    /**
     * 全局异常拦截处理(运行时异常)
     * <p>
     * http://localhost:8080/exception
     *
     * @param name
     * @return
     */
    @GetMapping("/exception")
    @ResponseBody
    public String exception(String name) {
        logger.info("hello");
        int i = 10 / 0;
        return "log:" + name;
    }

    /**
     * AOP切面类处理接口测试
     * <p>
     * http://localhost:8080/log?name=legend
     *
     * @param name
     * @return
     */
    @GetMapping("/log")
    @ResponseBody
    public String method(String name) {
        logger.info("hello");
        return "log:" + name;
    }
}
