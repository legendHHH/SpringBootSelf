package com.legend.springboot.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常拦截处理
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/12
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 运行时异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<String, String> runtimeException(RuntimeException e) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "500");
        map.put("message", "运行时异常,正在紧急修复中....");
        map.put("exception", e.getMessage());
        System.out.println("hello");
        return map;
    }

    /**
     * 空指针异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Map<String, String> nullPointException(NullPointerException e) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "501");
        map.put("message", "空指针异常,正在紧急修复中....");
        map.put("exception", e.getMessage());
        System.out.println("hello");
        return map;
    }
}
