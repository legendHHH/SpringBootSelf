package com.example.demo.thread;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/7/15
 */
public class CreateThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.print("i:"+i +"\t");
        }
    }
}
