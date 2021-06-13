package com.example.demo.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁测试
 * 可以避免死锁
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/13
 */
public class ReEntryLockDemo {

    /**
     * 可重入锁
     */
    Lock lock = new ReentrantLock();

    static Object objectLockA = new Object();

    public void method() {
        synchronized (objectLockA) {
            System.out.println("外层-----------------");
            synchronized (objectLockA) {
                System.out.println("中层-----------------");
            }
            synchronized (objectLockA) {
                System.out.println("内层-----------------");
            }

        }
    }

    public void method2() {
        try {
            lock.lock();
            System.out.println("外层-----------------");

            //加锁几次解锁几次，不然在多线程的环境中会造成死锁
            lock.lock();
            try {
                System.out.println("内层-----------------");
            } finally {
                lock.unlock();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReEntryLockDemo demo = new ReEntryLockDemo();
        new Thread(() -> {
            demo.method();
        }, "a").start();


        new Thread(() -> {
            demo.method2();
        }, "b").start();
        new Thread(() -> {
            demo.method2();
        }, "c").start();
    }
}
