package com.qcl.datastructure.sort;

/**
 * 直接插入排序算法
 * 首先设定插入次数，即循环次数 (for (int i = 1; i < length; i++) ),1个数的那次不用插入
 * 设定插入数和得到已经排好序列的最后一个数的位数
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/27
 */
public class DirectlyInserted {
    public static void main(String[] args) {
        //定义需要排序数组
        int[] a = {49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 1};

        System.out.println("直接插入排序前");
        for (int i : a) {
            System.out.print(i + "\t");
        }

        //数组长度
        int length = a.length;
        //要插入的数
        int insertNum;

        for (int i = 1; i < length; i++) {
            insertNum = a[i];
            int j = i - 1;
            //序列从后到前循环，将大于insertNum的数向后移动一格
            while (j >= 0 && a[j] > insertNum) {
                //元素移动一格
                a[j + 1] = a[j];
                j--;
            }
            //将需要插入的数放在要插入的位置。
            a[j + 1] = insertNum;
        }

        System.out.println("\n直接插入排序后");
        for (int i : a) {
            System.out.print(i + "\t");
        }
    }
}
