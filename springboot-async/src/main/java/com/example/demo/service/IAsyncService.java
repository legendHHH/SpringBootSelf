package com.example.demo.service;

import java.util.concurrent.Future;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/12
 */
public interface IAsyncService {
    void exampleTask();

    Future<Integer> addTask(int i);
}
