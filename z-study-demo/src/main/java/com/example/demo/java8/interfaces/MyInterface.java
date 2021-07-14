package com.example.demo.java8.interfaces;

/**
 * 自定义的函数式接口:作用是传入一个参数，返回一个参数。
 *
 * @param <T>
 */
@FunctionalInterface
public interface MyInterface<T> {
    T getValue(T t);
}
