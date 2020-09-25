package com.qcl.permission.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试controller
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/24
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        log.info("hello");
        return "hello, permission";
    }

    /*@RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) throws ParamException {
        log.info("validate");
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(module));
        BeanValidator.check(vo);
        return JsonData.success("test validate");
    }*/
}