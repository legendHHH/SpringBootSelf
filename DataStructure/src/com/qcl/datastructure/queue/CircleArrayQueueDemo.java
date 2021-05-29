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
public class CircleArrayQueueDemo {

    public static void main(String[] args) {

        //创建一个环形队列
        //设置了4，其队列的有效数据最大是3
        CircleArray queue = new CircleArray(4);

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
 * 使用环形数组模拟队列编写一个CircleArrayQueue类
 */
class CircleArray {
    /**
     * 表示数组的最大容量
     */
    private int maxSize;

    /**
     * 队列头
     * front变量的含义做一个调整：front就指向队列的第一个元素，也就是说arr[front] 就是队列的一个元素
     * front的初始值=0
     */
    private int front;

    /**
     * 队列尾
     * rear变量的含义做一个调整：rear只想队列的最后一个元素的后一个位置，因为希望空出一个空间作为约定
     * rear的初始值 = 0
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
    public CircleArray(int arrayMaxSize) {
        this.maxSize = arrayMaxSize;
        arr = new int[maxSize];
    }

    /**
     * 判断队列是否满
     *
     * @return
     */
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
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
        //直接将数据加入
        arr[rear] = data;

        //将rear后移,这里必须考虑取模
        rear = (rear + 1) % maxSize;
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

        //这里需要分析出front是指向队列的第一个元素
        //1.先把front对应的值保留到一个临时变量
        //2.将front后移
        //3.将临时保存的变量返回
        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;
    }

    /**
     * 显示队列的所有数据
     */
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列为空，没有数据");
        }
        //从front开始遍历,遍历多少个元素
        int size = this.size();
        for (int i = front; i < front + size; i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    /**
     * 求出当前队列有效数据的个数
     */
    public int size() {
        //rear = 1
        //front = 0
        //maxSize = 3

        //rear = 2
        //front = 1
        //maxSize = 3
        return (rear + maxSize - front) % maxSize;
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
