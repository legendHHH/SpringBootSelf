package com.qcl.datastructure.queue;

import java.util.Scanner;

/**
 * 使用数组模拟队列
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/28
 */
public class ArrayQueueDemo {

    public static void main(String[] args) {

        ArrayQueue queue = new ArrayQueue(3);

        //接收用户输入
        char key = ' ';
        Scanner sc = new Scanner(System.in);
        boolean loop = true;
        //输出一个菜单
        while (loop) {
            System.out.println("s(show)：显示队列");
            System.out.println("e(exit)：退出程序");
            System.out.println("a(add)：添加数据到队列");
            System.out.println("g(get)：从队列获取数据");
            System.out.println("h(head)：查询队列头数据");

            key = sc.next().charAt(0);

            switch (key) {
                case 's':
                    queue.showQueue();
                    break;

                case 'a':
                    System.out.println("请输入要添加的数据：");
                    int val = sc.nextInt();
                    boolean res = queue.addQueue(val);
                    System.out.println("添加结果：" + res);
                    break;

                case 'g':
                    try {
                        int queueData = queue.getQueueData();
                        System.out.printf("获取出的数据是 %d \n", queueData);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 'h':
                    try {
                        int queueData = queue.headQueue();
                        System.out.printf("队列头取出的数据是 %d \n", queueData);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 'e':
                    //System.exit(-1);
                    sc.close();
                    loop = false;
                    break;
                default:
                    System.out.println("end");
                    break;
            }
        }
        System.out.println("exit!!!!!");
    }
}


/**
 * 使用数组模拟队列编写一个ArrayQueue类
 */
class ArrayQueue {
    /**
     * 表示数组的最大容量
     */
    private int maxSize;

    /**
     * 队列头
     */
    private int front;

    /**
     * 队列尾
     */
    private int rear;

    /**
     * 该数据用于存放数据,模拟队列
     */
    private int[] arr;


    /**
     * 创建队列的构造器
     *
     * @param arrayMaxSize
     */
    public ArrayQueue(int arrayMaxSize) {
        this.maxSize = arrayMaxSize;
        arr = new int[maxSize];

        //指向队列头部，分析出front是只想队列头的前一个位置
        front = -1;

        //指向队列尾部，指向队列尾的数据(即就是队列最后一个数据)
        rear = -1;
    }

    /**
     * 判断队列是否满
     *
     * @return
     */
    public boolean isFull() {
        return rear == maxSize - 1;
    }

    /**
     * 判断队列是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return rear == front;
    }

    /**
     * 添加数据到队列
     *
     * @return
     */
    public boolean addQueue(int data) {
        if (isFull()) {
            System.out.println("队列满,不能加入数据~");
            return false;
        }
        //让rear后移
        rear++;
        arr[rear] = data;
        return true;
    }

    /**
     * 获取队列的数据出队列
     *
     * @return
     */
    public int getQueueData() {
        //判断队列是否为空
        if (isEmpty()) {
            throw new RuntimeException("队列为空,不能取数据");
        }
        //front后移
        front++;
        return arr[front];
    }

    /**
     * 显示队列的所有数据
     */
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列为空，没有数据");
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    /**
     * 显示队列的头数据注意不是取数据
     *
     * @return
     */
    public int headQueue() {
        if (isEmpty()) {
            System.out.println("队列为空，没有数据");
            throw new RuntimeException("队列为空,不能取数据");
        }
        return arr[front + 1];
    }


    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getFront() {
        return front;
    }

    public void setFront(int front) {
        this.front = front;
    }

    public int getRear() {
        return rear;
    }

    public void setRear(int rear) {
        this.rear = rear;
    }

    public int[] getArr() {
        return arr;
    }

    public void setArr(int[] arr) {
        this.arr = arr;
    }
}
