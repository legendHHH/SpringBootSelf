package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/12
 */
@Slf4j
@Service
public class AsyncServiceImpl implements IAsyncService {

    @Override
    @Async
    public void exampleTask() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("耗时任务-2-{}", Thread.currentThread().getId());
    }

    @Override
    @Async
    public Future<Integer> addTask(int n) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("计算任务-{}", Thread.currentThread().getId());

        return AsyncResult.forValue(n + 2);
    }

}
