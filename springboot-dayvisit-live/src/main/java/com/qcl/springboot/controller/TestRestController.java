package com.qcl.springboot.controller;

import com.qcl.springboot.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/11
 */
@RestController
@RequestMapping("test")
public class TestRestController {

    @Autowired
    private TestService testService;

    @RequestMapping("count")
    public String saveUserActivity(HttpServletRequest request){
        testService.saveUserActivity(request);
        return "保存成功";
    }
    
}