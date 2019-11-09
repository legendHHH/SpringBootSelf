package com.legend.springbootswagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "HelloController")
public class HelloController {

    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    @ApiOperation(value = "Mysql导出数据",notes = "mysql导出数据")
    public String test(){
        System.out.println("ddddd");
        return "1234";
    }
}
