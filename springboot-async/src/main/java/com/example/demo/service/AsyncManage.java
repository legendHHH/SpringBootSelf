package com.example.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
class AsyncManage {

    @Async
    public void asyncMethod() {
        System.out.println("2");
    }
}