package com.legend.retrofit.service;

import com.legend.retrofit.common.Result;
import com.legend.retrofit.entity.Person;
import com.legend.retrofit.http.MyHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试service
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/2
 */
@Service
public class TestService {

    @Autowired
    private MyHttpApi myHttpApi;

    /**
     * 保存操作
     */
    public void savePerson() {
        // 通过httpApi发起http请求
        Person person = new Person(1L,"Hello Retrofit");
        Result<Person> personResult = myHttpApi.savePerson(person);
        System.out.println(personResult.getMessage());
    }

    /**
     * 查询操作
     */
    public Person getPerson() {
        // 通过httpApi发起http请求
        Result<Person> personResult = myHttpApi.getPerson(1L);
        System.out.println(personResult.getMessage());
        return personResult.getData();
    }
}