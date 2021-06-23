package com.example.demo.jvm;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * JVM测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/23
 */
public class JVMDemo {

    public static void main(String[] args) {
        //VM细节详细情况
        System.out.println(VM.current().details());

        //所有的对象分配的字节都是8的整数倍
        System.out.println(VM.current().objectAlignment());

        //打印类指针
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        //GC年龄采用4位bit存储，最大为15.例如MaxTenuringThreshold参数默认值就是15  设置Idea的VM Options -XX:MaxTenuringThreshold=16
    }
}
