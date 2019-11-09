package com.legend.springbootswagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "TestController")
public class TestController {

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    @ApiOperation(value = "测试2Mysql导出数据",notes = "mysql导出数据")
    public String test(){
        System.out.println("ddddd");
        return "1234";
    }
}
