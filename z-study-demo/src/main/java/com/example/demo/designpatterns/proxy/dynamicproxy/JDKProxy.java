package com.example.demo.designpatterns.proxy.dynamicproxy;

import com.example.demo.designpatterns.proxy.dynamicproxy.factory.ProxyFactory;
import com.example.demo.designpatterns.proxy.staticproxy.IRentingHouse;
import com.example.demo.designpatterns.proxy.staticproxy.RentingHouseImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/11/23
 */
public class JDKProxy {

    public static void main(String[] args) {
        //委托对象--委托方
        IRentingHouse rentingHouse = new RentingHouseImpl();

        //使用工厂模式优化后
        IRentingHouse proxy = (IRentingHouse) ProxyFactory.getInstance().getJDKProxy(rentingHouse);

        //获取代理对象。动态生成代理对象
        /*IRentingHouse proxy = (IRentingHouse) Proxy.newProxyInstance(rentingHouse.getClass().getClassLoader(), rentingHouse.getClass().getInterfaces(),
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //写增强逻辑
                System.out.println("中介（代理）收取服务费3000元");
                //调用原有逻辑
                Object result = method.invoke(rentingHouse, args);
                System.out.println("客户信息卖了3毛钱");
                return result;
            }
        });*/
        proxy.rentHouse();
    }
}
