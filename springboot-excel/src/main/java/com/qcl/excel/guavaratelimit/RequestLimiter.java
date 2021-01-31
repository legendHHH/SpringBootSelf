package com.qcl.excel.guavaratelimit;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * RequestLimiter 自定义注解接口限流
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/31
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimiter {

    /**
     * 每秒创建令牌个数，默认:10
     *
     * @return
     */
    double QPS() default 10D;

    /**
     * 获取令牌等待超时时间 默认:500
     *
     * @return
     */
    long timeout() default 500;

    /**
     * 超时时间单位 默认:毫秒
     *
     * @return
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     * 无法获取令牌返回提示信息
     *
     * @return
     */
    String msg() default "亲，服务器快被挤爆了，请稍后再试！";
}
