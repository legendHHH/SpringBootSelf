package com.example.demo.queue;


import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 用两个队列来实现一个栈，完成栈的Push和Pop操作。
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/3/3
 */
public class DoubleQueueToStack {

    Queue<Integer> queue1 = new ArrayDeque<Integer>();
    Queue<Integer> queue2 = new ArrayDeque<Integer>();


    /**
     * 将元素x推入堆栈。
     *
     * @return
     */
    public void push(Integer element) {
        //两个队列为空时考虑queue1
        if (queue1.isEmpty() && queue2.isEmpty()) {
            queue1.add(element);
            return;
        }

        //如果queue1为空,queue2有数据,直接放入queue2
        if (queue1.isEmpty()) {
            queue2.add(element);
            return;
        }

        //如果queue2为空,queue1有数据,直接放入queue2
        if (queue2.isEmpty()) {
            queue1.add(element);
            return;
        }
    }


    /**
     * 取出栈中的数据
     *
     * @return
     */
    public Integer poll() {
        //两个队列为空时，直接抛出异常
        if (queue1.isEmpty() && queue2.isEmpty()) {
            throw new RuntimeException("stack is empty");
        }

        //如果queue1为空，将queue2中的元素依次加入到 queue1, 弹出最后一个元素
        if (queue1.isEmpty()) {
            while (queue2.size() > 1) {
                queue1.add(queue2.poll());
            }
            return queue2.poll();
        }

        //如果queue2为空，将queue1中的元素依次加入到 queue2, 弹出最后一个元素
        if (queue2.isEmpty()) {
            while (queue1.size() > 1) {
                queue2.add(queue1.poll());
            }
            return queue1.poll();
        }
        return null;
    }

    public static void main(String[] args) {
        DoubleQueueToStack qs = new DoubleQueueToStack();
        qs.push(2);
        qs.push(4);
        qs.push(7);
        qs.push(5);
        System.out.println(qs.poll());
        System.out.println(qs.poll());

        qs.push(1);
        System.out.println(qs.poll());
    }
}
