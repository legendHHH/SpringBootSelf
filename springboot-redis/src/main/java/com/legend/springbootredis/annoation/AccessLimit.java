package com.legend.springbootredis.annoation;

/**
 * 自定义注解(接口防刷的功能)
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/3/3
 */
public @interface AccessLimit {

    int seconds();
    int maxCount();
    boolean needLogin()default true;
}
