package com.example.demo.designpatterns.proxy.dynamicproxy.factory;

import com.example.demo.designpatterns.proxy.staticproxy.IRentingHouse;
import com.example.demo.designpatterns.proxy.staticproxy.RentingHouseImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理工厂：生成代理对象
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/11/23
 */
public class ProxyFactory {

    /**
     * 单例模式-饿汉式
     */
    private ProxyFactory() {
    }

    private static ProxyFactory proxyFactory = new ProxyFactory();

    public static ProxyFactory getInstance() {
        return proxyFactory;
    }

    /**
     * 使用JDK动态代理生成代理对象
     *
     * @param obj 委托对象
     * @return 代理对象
     */
    public Object getJDKProxy(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //写增强逻辑
                        System.out.println("中介（代理）收取服务费3000元");
                        //调用原有逻辑
                        Object result = method.invoke(obj, args);
                        System.out.println("客户信息卖了3毛钱");
                        return result;
                    }
                });
    }

    /**
     * 使用cglib动态代理生成代理对象
     *
     * @param obj 委托对象
     * @return 代理对象
     */
    public Object getCglibProxy(Object obj) {
        return Enhancer.create(obj.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("中介（代理）收取服务费3000元");
                Object result = method.invoke(obj, objects);
                System.out.println("客户信息卖了3毛钱");
                return result;
            }
        });
    }

}
