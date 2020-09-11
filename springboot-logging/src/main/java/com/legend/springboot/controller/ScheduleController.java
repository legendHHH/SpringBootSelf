package com.legend.springboot.controller;

import com.legend.springboot.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/9/12
 */
@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 定时任务(后台自动打印)
     *
     *
     * @return
     */
    @Scheduled(cron = "0/2 * * * * ? ")
    public String cron() {
        System.out.println("1111111111");
        return "HANDHello";
    }


    /**
     * 定时任务(异步处理)
     * http://localhost:8080/async
     *
     *
     * @return
     */
    @GetMapping("/async")
    public String async() {
        System.out.println("1111111111");
        scheduleService.async();
        return "HANDHello";
    }

    /**
     * 项目访问路径配置
     * http://localhost:8080/contextpath
     *
     *
     * @return
     */
    @GetMapping("/contextpath")
    public String contextPath() {
        return "HANDHello";
    }
}
