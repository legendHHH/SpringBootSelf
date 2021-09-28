package com.example.demo.queue;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 链表阻塞队列
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/9/28
 */
public class MyLinkedBlockingQueue {

    /**
     * 阻塞队列
     */
    private static LinkedBlockingQueue FRAMETASKQUEUE = new LinkedBlockingQueue(500);

    /**
     * <将消息放入队列中，若当前队列满，线程阻塞>
     *
     * @param msg msg
     * @throws
     */
    private static void produce(String msg) throws InterruptedException {
        System.out.println("start to produce msg.");
        FRAMETASKQUEUE.put(msg);
    }

    /**
     * <消费队列中数据，若当前队列无数据，线程阻塞>
     *
     * @return 消息
     * @throws
     */
    private static String consume() throws InterruptedException {
        System.out.println("start to consume msg.");
        return (String) FRAMETASKQUEUE.take();
    }


    public static void main(String[] args) {
        try {
            produce("我来了");
            consume();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ty = "";
        System.out.println(StringUtils.isBlank(ty));

        System.out.println(System.getProperty("java.io.tmpdir"));
    }
}
