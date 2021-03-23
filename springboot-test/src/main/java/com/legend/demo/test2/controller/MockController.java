package com.legend.demo.test2.controller;

import com.legend.demo.test2.domain.User;
import com.legend.demo.test2.service.MockService;
import com.yomahub.tlog.core.annotation.TLogAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Mock测试控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/19
 */
@Controller
public class MockController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockService mockService;

    @TLogAspect({"str","user.name","user.id","user.name"})
    @GetMapping("/test")
    @ResponseBody
    public String method(String str, User user){
        LOGGER.info("hello：{}，user：{}", str, user);
        return user.toString();
    }

    @GetMapping(value = "/editUser")
    public String editItem(Integer id, Model model) {
        LOGGER.info("hello Tlog测试1");
        User item = mockService.getUserById(id);
        model.addAttribute("user", item);
        return "userEdit";
    }

    @GetMapping(value = "/getUser")
    @ResponseBody
    public User getUser(Integer id) {
        LOGGER.info("hello Tlog测试2");
        User user = mockService.getUserById(id);
        return user;
    }
}
