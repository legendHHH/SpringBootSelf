package com.example.demo.lock;

import java.util.concurrent.TimeUnit;

/**
 * 死锁测试
 * 可以结合jconsole jps、jstack、jps -l命令排查问题
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/13
 */
public class DeadLockDemo {

    static Object objectLock = new Object();

    static Object objectLockA = new Object();
    static Object objectLockB = new Object();


    public static void main(String[] args) {
        DeadLockDemo deadLockDemo = new DeadLockDemo();

        new Thread(() -> {
            synchronized (objectLockA) {
                System.out.println(Thread.currentThread().getName() + "\t自己持有A锁定,期望获得B锁");
                try {
                    TimeUnit.SECONDS.sleep(1);

                    synchronized (objectLockB) {
                        System.out.println(Thread.currentThread().getName() + "\t成功获得B锁");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, "testThread--A").start();


        new Thread(() -> {
            synchronized (objectLockB) {
                System.out.println(Thread.currentThread().getName() + "\t自己持有B锁定,期望获得A锁");
                try {
                    TimeUnit.SECONDS.sleep(1);

                    synchronized (objectLockA) {
                        System.out.println(Thread.currentThread().getName() + "\t成功获得A锁");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, "testThread--B").start();
    }

    public synchronized void method() {
        System.out.println("hello");
    }

    public static synchronized void method2() {
        System.out.println("hello");
    }

    public String method3() {
        synchronized (objectLock) {
        }
        return null;
    }
}
