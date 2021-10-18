package com.legend.springbootmybatis.applicationrunner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 项目初始化完成后处理
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/10/16
 */
@Component
@Order(10)
public class MyAgentApplicationRun implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("项目启动完成,执行初始化方法......");
    }
}
