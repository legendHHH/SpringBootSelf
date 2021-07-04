package com.legend.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * 日志切面类
 *
 * @author legend
 * @description
 * @version 1.0
 * @date 2021/7/4
 */
@Aspect
public class LogAspect {

    /**
     * 抽取公共的切入点表达式
     * 1.本类引用
     * 2.其他切面引用
     */
    @Pointcut("execution(public int com.legend.spring.aop.MathCalculator.*(..))")
    public void pointCut(){

    }

    /**
     * @Before 在目标方法之前切入：切入点表达式(指定在那个方法切入)
     */
    //@Before("public int com.legend.spring.aop.MathCalculator.div(int,int))")
    //@Before("public int com.legend.spring.aop.MathCalculator.*(..))")
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("logStart...." + joinPoint.getSignature().getName());
        System.out.println("1." + joinPoint.getSignature().getName() + "运行.....参数列表：{" + Arrays.asList(args) + "}");
    }

    //@After("public int com.legend.spring.aop.MathCalculator.*(..))")
    @After("com.legend.spring.aop.LogAspect.pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println("2." + joinPoint.getSignature().getName() + "运行结束");
    }

    /**
     * JoinPoint joinPoint 一定要出现参数表的第一位,否则会无法运行
     *
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println("3." + joinPoint.getSignature().getName() + "运行.....@AfterReturning运行结果：{" + result + "}");
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        System.out.println("4." + joinPoint.getSignature().getName() + "运行.....异常信息：{" + exception + "}");
    }

}
