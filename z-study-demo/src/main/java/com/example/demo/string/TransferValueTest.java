package com.example.demo.string;

/**
 * 8种基本类型的变量+对象的引用变量+实例方法都是在函数的栈内存中实现
 * 栈存储的是：本地变量(输入参数和输出参数以及方法内的变量)、栈操作(记录出栈入栈操作)、栈帧数据(类文件、方法)
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/3/17
 */
public class TransferValueTest {


    public void changeMethod1(int age) {
        age = 20;
    }

    public void changeMethod2(Person person) {
        person.setPersonName("legend");
    }

    public void changeMethod3(String str) {
        str = "666";
    }

    public static void main(String[] args) {
        TransferValueTest ts = new TransferValueTest();
        int age = 200;
        ts.changeMethod1(age);
        System.out.println("---------age：" + age);


        Person p = new Person();
        p.setPersonName("54321");
        ts.changeMethod2(p);
        System.out.println("---------person：" + p.getPersonName());

        String str = "adbv";
        ts.changeMethod3(str);
        System.out.println("---------string：" + str);
    }
}
