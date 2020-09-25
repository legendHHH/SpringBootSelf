package com.qcl.permission.controller;

import com.qcl.permission.common.JsonData;
import com.qcl.permission.exception.ParamException;
import com.qcl.permission.exception.PermissionException;
import com.qcl.permission.mapper.SysAclModuleMapper;
import com.qcl.permission.model.SysAclModule;
import com.qcl.permission.param.TestVo;
import com.qcl.permission.utils.BeanValidator;
import com.qcl.permission.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

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

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        log.info("hello");
        return "hello, permission";
    }

    @RequestMapping(value = "/hello.json", method = RequestMethod.GET)
    @ResponseBody
    public JsonData helloJson() {
        log.info("helloJson");
        return JsonData.success("hello Json, permission");
    }

    @RequestMapping(value = "/hello2.json", method = RequestMethod.GET)
    @ResponseBody
    public JsonData helloJsonException() {
        log.info("helloJson");
        throw new PermissionException("test permission exception");
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) {
        log.info("validate");
        try {
            Map<String, String> map = BeanValidator.validateObject(vo);
            if (map != null && map.entrySet().size() > 0) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    log.info("{}--->{}", entry.getKey(), entry.getValue());
                }
            }
        } catch (ParamException e) {
            e.printStackTrace();
        }
        return JsonData.success("test validate");
    }


    @RequestMapping("/validate2.json")
    @ResponseBody
    public JsonData validate2(TestVo vo) throws ParamException {
        log.info("validate");
//        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
//        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
//        log.info(JsonMapper.obj2String(module));
        BeanValidator.check(vo);
        return JsonData.success("test validate");
    }
}
