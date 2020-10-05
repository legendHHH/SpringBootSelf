package com.legend.retrofit.controller;

import com.google.common.base.Preconditions;
import com.legend.retrofit.common.Result;
import com.legend.retrofit.common.ResultCode;
import com.legend.retrofit.entity.Person;
import com.legend.retrofit.mapper.PersonMapper;
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
public class PersonController {

    private Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonMapper personMapper;

    /**
     * 保存人类
     *
     * @param person
     * @return
     */
    @PostMapping("/api/test/savePerson")
    public Result<Person> savePerson(Person person) {
        logger.info("/api/test/savePerson保存操作开始....");
        int result = personMapper.savePerson(person);
        Result<Person> personResult = new Result<>();
        if (result > 0) {
            personResult.setCode(ResultCode.SUCCESS);
            personResult.setData(person);
            personResult.setMessage("成功");
        }
        return personResult;
    }

    @GetMapping("/api/test/person")
    public Result<Person> getPerson(Long id) {
        Result<Person> personResult = new Result<>();
        Person person = personMapper.getPerson(id);
        Preconditions.checkNotNull(person, "查询的对象不存在");
        personResult.setCode(ResultCode.SUCCESS);
        personResult.setData(person);
        personResult.setMessage("成功");
        return personResult;
    }
}
