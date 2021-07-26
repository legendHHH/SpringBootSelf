package com.qcl.redislock.aspect;

import com.qcl.redislock.annoations.RedisLockAnnotation;
import com.qcl.redislock.enums.RedisLockTypeEnum;
import com.qcl.redislock.holder.RedisLockDefinitionHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * 核心切面拦截的操作
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/4
 */
@Slf4j
@Component
@Aspect
public class RedisLockAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 存储目前有效的key定义
     */
    private ConcurrentLinkedQueue<RedisLockDefinitionHolder> holderList = new ConcurrentLinkedQueue();

    /**
     * @annotation 中的路径表示拦截特定注解
     */
    @Pointcut("@annotation(com.qcl.redislock.annoations.RedisLockAnnotation)")
    public void redisLockPC() {
    }


    /**
     * 环绕通知=前置+目标方法执行+后置通知
     *
     * 带参数：@Around(value = "redisLockPC() && args(id)")
     * public Object around(ProceedingJoinPoint proceedingJoinPoint, Integer id) throws Throwable {
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "redisLockPC()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Method method = this.resolveMethod(proceedingJoinPoint);
        RedisLockAnnotation annotation = method.getAnnotation(RedisLockAnnotation.class);
        RedisLockTypeEnum redisLockTypeEnum = annotation.typeEnum();
        Object[] params = proceedingJoinPoint.getArgs();
        String ukString = params[annotation.lockFiled()].toString();

        //redis的key
        String businessKey = redisLockTypeEnum.getUniqueKey(ukString);
        String uniqueValue = UUID.randomUUID().toString();
        // 加锁
        Object result = null;
        try {
            Boolean isSuccess = redisTemplate.opsForValue().setIfAbsent(businessKey, uniqueValue);
            if (!isSuccess) {
                throw new Exception("You can't do it，because another has get the lock =-=");
            }

            redisTemplate.expire(businessKey, annotation.lockTime(), TimeUnit.SECONDS);

            Thread currentThread = Thread.currentThread();

            RedisLockDefinitionHolder redisLockDefinitionHolder = new RedisLockDefinitionHolder(businessKey, annotation.lockTime(), System.currentTimeMillis(),
                    currentThread, annotation.tryCount());
            holderList.add(redisLockDefinitionHolder);

            //启动目标方法执行的
            result = proceedingJoinPoint.proceed();

            // 线程被中断，抛出异常，中断此次请求
            if (currentThread.isInterrupted()) {
                throw new InterruptedException("You had been interrupted =-=");
            }

        } catch (InterruptedException e ) {
            log.error("Interrupt exception, rollback transaction", e);
            throw new Exception("Interrupt exception, please send request again");
        } catch (Exception e) {
            log.error("has some error, please check again", e);
        } finally {
            // 请求结束后，强制删掉 key，释放锁
            redisTemplate.delete(businessKey);
            log.info("release the lock, businessKey is [" + businessKey + "]");
        }
        return result;

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
