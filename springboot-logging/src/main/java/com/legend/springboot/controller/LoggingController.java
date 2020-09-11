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
     * AOP切面类处理接口测试
     *
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
