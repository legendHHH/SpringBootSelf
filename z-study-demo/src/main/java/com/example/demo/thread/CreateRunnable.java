package com.example.demo.thread;

/**
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/7/15
 */
public class CreateRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i< 10; i++) {
            System.out.println("i:" + i);
        }

    }
}
