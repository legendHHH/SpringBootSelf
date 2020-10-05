package com.legend.retrofit.controller;

import com.legend.retrofit.common.Result;
import com.legend.retrofit.common.ResultCode;
import com.legend.retrofit.entity.Person;
import com.legend.retrofit.mapper.PersonMapper;
import com.legend.retrofit.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/10/5
 */
@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @GetMapping("/savePerson")
    public void sp() {
        testService.savePerson();
    }

    @GetMapping("/getPerson")
    public Person gp() {
        logger.info("查询操作开始....");
        return testService.getPerson();
    }
}
