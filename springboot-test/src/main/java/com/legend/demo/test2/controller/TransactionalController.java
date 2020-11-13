package com.legend.demo.test2.controller;

import com.legend.demo.test2.service.TransactionalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试事务的传播性案例
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/13
 */
@RestController
public class TransactionalController {

    @Resource
    private TransactionalService transactionalService;

    @GetMapping(value = "/tx")
    public String method(){
        System.out.println("hello");
        String txMethod = transactionalService.txMethod();
        return txMethod;
    }
}
