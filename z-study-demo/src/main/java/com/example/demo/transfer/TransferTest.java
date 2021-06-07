package com.example.demo.transfer;

/**
 * 异不使用新的变量，交换两个变量的值(异或运算测试)
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/6
 */
public class TransferTest {

    public static void main(String[] args) {
        //不使用新的变量，交换两个变量的值
        int a = 10;
        int b = 12;

        //异或运算：相同为0，不同为1
        //1010  1100 得到的是一个中间值
        a = a ^ b;

        //真正开始进行交换
        b = a ^ b;
        a = a ^ b;

        //a=12,b=10
        System.out.println("a===>" + a);
        System.out.println("b===>" + b);

        //快速判断两个值是否相等
        int c = 6;
        int d = 0;
        System.out.println((c ^ d) == 0);
    }
}
