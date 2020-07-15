package com.example.demo.thread;

/**
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/7/15
 */
public class ThreadDemo {
    public static void main(String[] args) {
        System.out.println("-----多线程创建开始-----");
        Thread threadNi = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("i:" + i);
                }
            }
        });
        threadNi.start();
        System.out.println("-----多线程创建结束-----");


        System.out.println("************2.多线程创建开始************");
        // 1.创建一个线程
        CreateRunnable createRunnable = new CreateRunnable();
        // 2.开始执行线程 注意 开启线程不是调用run方法，而是start方法
        System.out.println("************多线程启动************");
        Thread thread = new Thread(createRunnable);
        thread.start();
        System.out.println("************多线程创建结束************");


        System.out.println("************1.多线程创建开始************");
        // 1.创建一个线程
        CreateThread createThread = new CreateThread();
        // 2.开始执行线程 注意 开启线程不是调用run方法，而是start方法
        System.out.println("************多线程启动************");
        long id = createThread.getId();
        createThread.start();
        System.out.println("************多线程创建结束************");
    }
}
