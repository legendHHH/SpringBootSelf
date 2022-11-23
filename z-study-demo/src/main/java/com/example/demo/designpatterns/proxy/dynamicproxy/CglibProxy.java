package com.example.demo.designpatterns.proxy.dynamicproxy;

import com.example.demo.designpatterns.proxy.dynamicproxy.factory.ProxyFactory;
import com.example.demo.designpatterns.proxy.staticproxy.RentingHouseImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib动态代理使用的是第三方的需要第三方jar包支持
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/11/23
 */
public class CglibProxy {

    public static void main(String[] args) {
        //委托对象--委托方
        RentingHouseImpl rentingHouse = new RentingHouseImpl();

        //获取rentingHouse对象的代理对象,
        // Enhancer类似于JDK动态代理中的Proxy
        //通过实现接口MethodInterceptor能够对各个方法进行拦截增强，类似于JDK动态代理中的InvocationHandler

        //使用工厂来获取代理对象
        RentingHouseImpl proxy = (RentingHouseImpl) ProxyFactory.getInstance().getCglibProxy(rentingHouse);

        /*RentingHouseImpl proxy = (RentingHouseImpl) Enhancer.create(rentingHouse.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("中介（代理）收取服务费3000元");
                Object result = method.invoke(rentingHouse, objects);
                System.out.println("客户信息卖了3毛钱");
                return result;
            }
        });*/
        proxy.rentHouse();
    }
}
