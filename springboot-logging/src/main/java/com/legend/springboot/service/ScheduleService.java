package com.legend.springboot.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/12
 */
@Service
public class ScheduleService {

    @Async
    public String async() {

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(2000);
                System.out.println("异步线程开始处理:" +Thread.currentThread().getName());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return "HANDHello";
    }
}
