package com.qcl.webssh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * webssh的入口
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/18
 */
@Controller
public class RouterController {

    @GetMapping("/websshpage")
    public String websshpage() {
        System.out.println("进入接口....");
        return "webssh";
    }
}
