package com.example.demo.designpatterns.proxy;

/**
 * 代理模式测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/10/16
 */
public class ProxyTest {

    public static void main(String[] args) {
        System.out.println("不使用代理类，调用doSomething");
        Person person = new Bob();
        person.doSomething();

        System.out.println("========================================");

        System.out.println("使用代理类，调用doSomething");
        Person proxy = (Person) new JDKDynamicProxy(new Bob()).getTarget();
        proxy.doSomething();
    }
}
