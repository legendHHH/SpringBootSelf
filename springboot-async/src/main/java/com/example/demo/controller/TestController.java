package com.example.demo.controller;

import com.example.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/5/19
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/query-time")
    public String testAsync(){
        Long time = System.currentTimeMillis();

        //异步调用
        this.asyncMethod();

        //
        Long time2 = System.currentTimeMillis() - time;
        log.info("整个方法的时间计算:{}",time2);
        return time2.toString();
    }


    @GetMapping("/query")
    public String testAsync2(){
        Long time = System.currentTimeMillis();
        //异步调用
        testService.testMethod();
        Long time2 = System.currentTimeMillis() - time;
        log.info("整个方法的时间计算:{}",time2);
        return time2+"";
    }

    @Async("testTaskExecutor")
    public String asyncMethod() {
        Long time = System.currentTimeMillis();

        String a = "a" + "b";
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long time2 = System.currentTimeMillis() - time;
        log.info("异步方法内的时间计算:{}",time2);
        return a;
    }

    /**
     * 有异步注解但是却同步执行
     *
     * @return
     */
    @GetMapping("/invalid")
    public String invalidAsyncExample() {
        testService.invalidAsyncExample();
        return "测试完成 " + LocalDateTime.now().toString();
    }

    /**
     * 无返回值的异步任务
     *
     * @return
     */
    @GetMapping("/no-value")
    public String noValueAsyncExample() {
        testService.noValueAsyncExample();
        return "测试完成 " + LocalDateTime.now().toString();
    }

    /**
     * 有返回值的异步任务
     *
     * @return
     */
    @GetMapping("/value")
    public int valueAsyncExample() {
        return testService.valueAsyncExample();
    }

}
