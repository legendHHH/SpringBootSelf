package com.example.demo.sso.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 登录的控制器
 *
 * @author legend
 */
@RequestMapping("/sso")
public class LoginController {

    @RequestMapping("/login")
    public String login(String username, String password, HttpServletRequest req) {
        this.checkLoginInfo(username, password);
        req.getSession().setAttribute("isLogin", true);
        return "success";
    }

    private void checkLoginInfo(String username, String password) {
        //查询数据库信息

    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session != null) {
            session.invalidate();//触发LogoutListener
        }
        return "redirect:/";
    }


    private String getToken() {
        return UUID.randomUUID().toString();
    }


    public static void main(String[] args) {
        String s = "100101";
        List<String> list = Arrays.asList("I", "AM", "LEGEND", "10010");
        if (list.contains(s.toLowerCase()) || list.contains(s.toUpperCase())) {
            System.out.println("输入相同");
        } else {
            System.out.println("输入不同");
        }

    }

}
