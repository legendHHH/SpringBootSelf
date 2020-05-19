package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TestService{

    @Autowired
    private AsyncManage asyncManage;

    @Async
    public void testMethod(){
        try {
            System.out.println("1");
            Thread.sleep(10000);
            asyncManage.asyncMethod();
            System.out.println("3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
