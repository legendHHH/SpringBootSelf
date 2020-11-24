package com.qcl.echarts.iputil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/22
 */
@RestController("user")
public class LoginController {

    @GetMapping("login")
    public String login() {
        //登录逻辑
        return "success";
    }
}
