package com.example.demo.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 卖票案例
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/14
 */
public class SaleTicketDemo {
    Logger logger = LoggerFactory.getLogger(SaleTicketDemo.class);

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
/*        new Thread(() -> {
            for (int i = 0; i < 32; i++) {
                ticket.sale();
            }
        }, "a").start();

        new Thread(() -> {
            for (int i = 0; i < 32; i++) {
                ticket.sale();
            }
        }, "b").start();

        new Thread(() -> {
            for (int i = 0; i < 32; i++) {
                ticket.sale();
            }
        }, "c").start();

        System.out.println("======================");

        //暂停几秒线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        //使用线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            for (int i = 0; i < 32; i++) {
                threadPool.execute(() -> {

                    ticket.sale();
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

}

class Ticket {
    private int number = 30;

    /**
     * 默认就是非公平锁(性能好) 并且是可重入锁
     */
    Lock lock = new ReentrantLock(true);

    /**
     * 创建公平锁(创建队列)
     */
    //Lock lock = new ReentrantLock(true);
    public void sale() {
        lock.lock();

        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "\t卖出第：" + (number--) + "\t还剩下：" + number);
            }
        } finally {
            lock.unlock();
        }
    }
}
