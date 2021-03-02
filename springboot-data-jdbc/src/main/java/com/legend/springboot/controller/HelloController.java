package com.legend.springboot.controller;

import com.alibaba.druid.filter.config.ConfigTools;
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

    public static void main(String[] args) throws Exception {
        // 需要加密的明文命名
        String password = "123456"; // 【注意：这里要改为你自己的密码】
        // 调用 druid 生成私钥、公钥、密文
        ConfigTools.main(new String[]{password});

        String privateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAoa5rY4eJMpQHj60Fua4qWrueHP4PGHsixWJorJfTO0Sh3mIBh14dFKtG6bA9OSTEtaKOQR232oBICbgG1kTCbwIDAQABAkAyJk/ZB2le7tuJjTmBZqxsiFV62LMF/OvEHcop/s3eS+RIy7ocEB5sXImRFLCFBXjXY41BjmuQU2BnaiFMv3zBAiEA51RVYcc1+mYz2Bf8lBO0mjeZB8eC5VdqW+ZJbNgLSGsCIQCy7JSYzTnV9MBMshbGvpmZqBgtDCV8yqE/UiWms49/DQIgdbBkF/C/t3rTmXbqm3NDCMqMYnbQU1mb1NFePa/UXa0CIHmT8FTz/ry3loOG75sUoGKcqfDSNBbbyT2xoAvqLzNxAiEAvwGDJdoshsULOA+LP4ezLonY7mYwFvKjHcZWjqPRhMI=";
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKGua2OHiTKUB4+tBbmuKlq7nhz+Dxh7IsViaKyX0ztEod5iAYdeHRSrRumwPTkkxLWijkEdt9qASAm4BtZEwm8CAwEAAQ==";
        String password2 = "AvY2gZrjNmQjJIqlir3VSQib1KO3TIrKT3V9oNKgOIzS6c9cUR1ZLOMsoscf2XylZt6qauZFXbstHRqXkb0QWg==";
        String decrypt = ConfigTools.decrypt(publicKey, password2);
        System.out.println(decrypt);
    }

}
