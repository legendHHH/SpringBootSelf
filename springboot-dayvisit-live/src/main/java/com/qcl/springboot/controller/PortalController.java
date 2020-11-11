package com.qcl.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/11
 */
@Controller
@RequestMapping("portal")
public class PortalController {

    @RequestMapping("index")
    public String index(){
        return "portal/index";
    }

}