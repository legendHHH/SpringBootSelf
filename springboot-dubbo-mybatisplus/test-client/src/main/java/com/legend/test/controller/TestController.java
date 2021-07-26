package com.legend.test.controller;

import com.legend.test.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
 
/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author funkye
 * @since 2019-03-20
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试接口")
public class TestController {
 
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    @Lazy
    DemoService demoService;
 
    @GetMapping(value = "testSeataOne")
    @ApiOperation(value = "测试手动回滚分布式事务接口")
    public Object testSeataOne() {
        return demoService.One();
    }
 
    @GetMapping(value = "testSeataTwo")
    @ApiOperation(value = "测试异常回滚分布式事务接口")
    public Object testSeataTwo() {
        return demoService.Two();
    }
 
}