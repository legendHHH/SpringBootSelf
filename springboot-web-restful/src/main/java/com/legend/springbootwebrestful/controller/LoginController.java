package com.legend.springbootwebrestful.controller;


import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 开发技巧
 *      禁用模板引擎缓存
 *      页面修改完成后  ctrl+f9 重新编译
 */
@Controller
public class LoginController {


    @PostMapping(value = "/user/login")
    //@RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map,
                        HttpSession session){


        if (!StringUtils.isEmpty(username) && "1234".equals(password)){
            //登陆成功
            session.setAttribute("loginUser",username);
            //防止表单重复提交(使用视图映射)
            return "redirect:/main.html";
        }else {
            //登陆失败
            map.put("msg","用户名或密码错误");
            return "login";
        }
    }
}
