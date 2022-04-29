package com.qcl.webssh.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 自动启动浏览器并打开指定页面
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/19
 */
@Component
public class OpenBrowser implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("应用已经准备就绪 ... 启动浏览器并自动加载指定的页面 ... ");
        try {
            //指定自己项目的路径
            Runtime.getRuntime().exec("cmd   /c   start   http://localhost:8080/end/page/login.html");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}