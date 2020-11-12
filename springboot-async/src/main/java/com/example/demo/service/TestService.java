package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class TestService {

    private Logger log = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private AsyncManage asyncManage;

    @Autowired
    private IAsyncService asyncService;

    @Async
    public void testMethod() {
        try {
            System.out.println("1");
            Thread.sleep(10000);
            asyncManage.asyncMethod();
            System.out.println("3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 流程-1-36
     * 流程-2-36
     * 流程-3-36
     * <p>
     * 结果是同步执行，你调用的类需要Spring帮你代理，然后才能异步去执行
     * invalidAsyncTask(); 调用的方法很明确，不需要代理，这时候Spring也就不能帮你异步去执行了。
     */
    public void invalidAsyncExample() {
        log.info("流程-1-{}", Thread.currentThread().getId());
        //异步任务
        invalidAsyncTask();
        log.info("流程-3-{}", Thread.currentThread().getId());
    }

    @Async
    public void invalidAsyncTask() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("流程-2-{}", Thread.currentThread().getId());
    }

    public void noValueAsyncExample() {
        log.info("流程-1-{}", Thread.currentThread().getId());
        asyncService.exampleTask();
        log.info("流程-3-{}", Thread.currentThread().getId());
    }

    public int valueAsyncExample() {
        int result = 0;

        long startTime = System.currentTimeMillis();

        List<Future<Integer>> futureList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Future<Integer> future = asyncService.addTask(i);
            futureList.add(future);
        }

        for (Future<Integer> f : futureList) {
            Integer value = null;
            try {
                value = f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            if (value != null) {
                result += value;
            }
        }

        long endTime = System.currentTimeMillis();

        log.info("耗时 {} s", (endTime - startTime) / 1000D);

        return result;
    }
}
