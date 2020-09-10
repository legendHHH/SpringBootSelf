package com.legend.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/10
 */
@Controller
public class HelloController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 插入数据
     * http://localhost:8080/add?departmentName=legend
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/add")
    public String map(String departmentName) {
        int result = jdbcTemplate.update("INSERT INTO `springboot`.`department`(`department_name`) VALUES (?)", departmentName);
        return "添加结果情况：" + result;
    }

    /**
     * 查询数据
     * http://localhost:8080/query
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/query")
    public Map<String, Object> map() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * FROM department");
        return list.get(0);
    }
}
