package com.legend.springboot.controller;

import com.legend.springboot.annotation.Log;
import com.legend.springboot.aop.LoggingAop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Log注解方式的日志测试
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/9/11
 */
@RestController
public class LogAnnotationController {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(LogAnnotationController.class);

    /**
     * Log注解形式的AOP切面类处理接口测试
     *
     * http://localhost:8080/log-annotation?name=legend
     *
     * @param name
     * @return
     */
    @Log
    @GetMapping("/log-annotation")
    @ResponseBody
    public String method(String name) {
        logger.info("hello");
        return "log:" + name;
    }
}
