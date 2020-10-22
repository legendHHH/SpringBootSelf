package com.qcl.echarts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/22
 */
@Controller
public class IndexController {

    @RequestMapping("/echarts")
    public String echarts(Model model) {
        System.out.println("IndexController.echarts");
        return "echarts";
    }

}