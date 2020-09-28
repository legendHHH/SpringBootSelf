package com.qcl.permission.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Admin Controller
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/27
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("index.page")
    public ModelAndView index() {
        return new ModelAndView("admin");
    }
}
