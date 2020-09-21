package com.qcl.springsecurity.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试安全控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/21
 */
@RestController
public class SecurityController {

    /**
     * 这个接口是不拦截得,其他都是需要拦截得
     *
     * @return
     */
    @RequestMapping("/")
    public String home() {
        return "hello spring boot";
    }


    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }

    /**
     * 有指定角色,每个角色有指定的权限
     * <p>
     * 在主启动类中使用@EnableGlobalMethodSecurity 让这个PreAuthorize注解生效
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/roleAuth")
    public String role() {
        return "admin auth";
    }


    /**
     * PostAuthorize注解是在调用方法完成之后进行权限检查
     *
     * @param id
     * @param username
     * @param user
     * @return
     */
    @PreAuthorize("#id<10 and principal.username.equals(#username) and #user.username.equals('abc')")
    @PostAuthorize("returnObject%2==0") //在调用方法完成后进行权限检查,判断方法返回值是不是偶数的 returnObject能获取到返回值
    @RequestMapping("/test")
    public Integer test(Integer id, String username, User user) {
        // ...
        return id;
    }

    /**
     * PreFilter是对集合类的数据进行处理
     *
     * @param idList
     * @return
     */
    @PreFilter("filterObject%2==0")
    @PostFilter("filterObject%4==0") //是对集合类的参数进行过滤
    @RequestMapping("/test2")
    public List<Integer> test2(List<Integer> idList) {
        // ...
        return idList;
    }
}
