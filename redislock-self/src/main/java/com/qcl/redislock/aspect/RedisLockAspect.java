package com.qcl.redislock.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 核心切面拦截的操作
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/4
 */
@Component
@Aspect
public class RedisLockAspect {

    /**
     * @annotation 中的路径表示拦截特定注解
     */
    @Pointcut("@annotation(com.qcl.redislock.annoations.RedisLockAnnotation)")
    public void redisLockPC() {
    }

    @Around(value = "redisLockPC()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        Method method = resolveMethod(proceedingJoinPoint);
    }

    private Method resolveMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Class<?> targetClass = proceedingJoinPoint.getTarget().getClass();
        Method method = getDeclaredMethodFor(
                targetClass,
                methodSignature.getName(),
                methodSignature.getMethod().getParameterTypes());

        if (method == null) {
            throw new IllegalStateException("Cannot resolve target method: " + methodSignature.getMethod().getName());
        }

        return method;
    }

    /**
     * 获取指定类上的指定方法
     *
     * @param clazz          指定类
     * @param name           指定方法
     * @param parameterTypes 参数类型列表
     * @return 找到就返回method，否则返回null
     */
    public static Method getDeclaredMethodFor(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getDeclaredMethodFor(superClass, name, parameterTypes);
            }
        }
        return null;
    }

}
