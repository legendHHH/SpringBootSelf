package com.qcl.datastructure.linkedlist;

/**
 * 约瑟夫问题
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/28
 */
public class Josepfu {

    public static void main(String[] args) {
        //测试
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        //加入五个小孩节点
        circleSingleLinkedList.addBoy(5);

        circleSingleLinkedList.showBoy();
    }
}

/**
 * 创建环形的单向链表
 */
class CircleSingleLinkedList {
    /**
     * 创建第一个first节点,当前没有编号
     */
    private Boy first = new Boy(-1);

    /**
     * 添加一个小孩
     *
     * @param nums
     */
    public void addBoy(int nums) {
        if (nums < 1) {
            System.out.println("输入的值不正确");
            return;
        }

        //辅助指针,帮助构建环形链表
        Boy curBoy = null;
        for (int i = 1; i <= nums; i++) {
            //根据编号,创建小孩节点
            Boy boy = new Boy(i);

            //如果是第一个小孩
            if (i == 1) {
                first = boy;
                //构成环
                first.setNext(first);
                //让curBoy指向第一个小孩
                curBoy = first;
            } else {
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }

    /**
     * 遍历当前环形链表
     */
    public void showBoy() {
        //判断链表是否为空
        if (first == null) {
            System.out.println("没有任何小孩");
            return;
        }

        //因为first不能动,因此需要一个辅助指针
        Boy curBoy = first;
        while (true) {
            System.out.printf("小孩的编号 %d \n", curBoy.getNo());

            //说明已经遍历结束
            if (curBoy.getNext() == first) {
                System.out.println();
                break;
            }
            //curBoy后移
            curBoy = curBoy.getNext();
        }
    }
}

/**
 * 创建一个Boy类,表示一个节点
 */
class Boy {
    /**
     * 编号
     */
    private int no;

    /**
     * 指向下一个节点
     */
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}