package com.legend.springbootredis.controller;

import com.legend.springbootredis.annoation.AccessLimit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/3/3
 */
@Controller
public class FangshuaController {

    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @RequestMapping("/fangshua")
    @ResponseBody
    public Object fangshua() {
        return "请求成功";
    }
}