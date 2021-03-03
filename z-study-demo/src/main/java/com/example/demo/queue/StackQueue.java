package com.example.demo.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 队列栈
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/3/3
 */
public class StackQueue {

    public Queue<Integer> queue;

    public StackQueue() {
        queue = new LinkedList<>();
    }

    public void push(int x) {
        queue.add(x);
        int size = queue.size();
        while (size-- > 1) {
            queue.add(queue.poll());
        }
    }

    /**
     * 将元素x推入堆栈。
     *
     * @return
     */
    public int pop() {
        return queue.poll();
    }

    /**
     * Get the top element
     * 用来返回队列的头元素,不删除
     *
     * @return
     */
    public int top() {
        return queue.peek();
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return queue.size() == 0;
    }
}
