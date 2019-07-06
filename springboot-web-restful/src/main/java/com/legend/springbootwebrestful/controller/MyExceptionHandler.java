package com.legend.springbootwebrestful.controller;

import com.legend.springbootwebrestful.exception.UserNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理器
 */
@ControllerAdvice
public class MyExceptionHandler {

    //1.浏览器/客户端都是json数据(没有自适应效果)
    /*@ResponseBody
    @ExceptionHandler(UserNotExistException.class)
    public Map<String,Object> handleException(Exception e){
        Map<String,Object> map = new HashMap<>();
        map.put("code","user.code");
        map.put("message",e.getMessage());
        return map;
    }*/

    //进行自适应效果
    /*@ExceptionHandler(UserNotExistException.class)
    public String handleException(Exception e, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //传入我们自己的错误状态码
        request.setAttribute("javax.servlet.error.status_code",500);
        map.put("code","user.code");
        map.put("message",e.getMessage());

        //转发到/error
        return "forward:/error";
    }*/


    @ExceptionHandler(UserNotExistException.class)
    public String handleException(Exception e, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //传入我们自己的错误状态码
        request.setAttribute("javax.servlet.error.status_code",500);
        map.put("code","user.code");
        map.put("message","用户出错啦");

        //将定制的
        request.setAttribute("ext",map);
        //转发到/error
        return "forward:/error";
    }
}
