package com.qcl.redislock.annoations;

import com.qcl.redislock.enums.RedisLockTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设定被拦截的注解名字
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RedisLockAnnotation {

    /**
     * 特定参数识别，默认取第 0 个下标
     */
    int lockFiled() default 0;

    /**
     * 超时重试次数
     */
    int tryCount() default 3;

    /**
     * 自定义加锁类型
     */
    RedisLockTypeEnum typeEnum();

    /**
     * 释放时间，秒 s 单位
     */
    long lockTime() default 30;
}
