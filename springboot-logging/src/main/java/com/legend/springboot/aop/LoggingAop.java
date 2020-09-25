package com.legend.springboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

/**
 * 日志AOP切面
 * https://blog.csdn.net/weixin_34014277/article/details/91369520
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/11
 */
@Aspect
@Component
public class LoggingAop {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(LoggingAop.class);

    /**
     * 定义一个切入点表达式,用来确定哪些类需要代理
     * execution(* com.legend.springboot.controller.*.*(..)) controller包下所有类的所有方法都会被代理
     */
    @Pointcut("execution(* com.legend.springboot.controller.LoggingController.*(..))")
    public void pointCut() {
    }

    /**
     * 前置方法,在目标方法执行前执行
     *
     * @param joinPoint 封装了代理方法信息的对象,若用不到则可以忽略不写
     */
    @Before(value = "pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("进入了前置方法......");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestUrl = request.getRequestURL().toString();
        String method = request.getMethod();
        String remoteAddr = request.getRemoteAddr();
        String session = request.getSession().toString();
        logger.info("URL:{}", requestUrl);
        logger.info("method:{}", method);
        logger.info("remoteAddr:{}", remoteAddr);
        logger.info("session:{}", session);

        Enumeration<String> requestAttributeNames = request.getAttributeNames();
        while (requestAttributeNames.hasMoreElements()) {
            String key = requestAttributeNames.nextElement();
            Object value = request.getAttribute(key);
            logger.info("key:{},value:{}", key, value);
        }


        logger.info("目标方法名为:{}", joinPoint.getSignature().getName());
        logger.info("目标方法所属类的简单类名:{}", joinPoint.getSignature().getDeclaringType().getSimpleName());
        logger.info("目标方法所属类的类名:{}", joinPoint.getSignature().getDeclaringTypeName());
        logger.info("目标方法声明类型:{}", Modifier.toString(joinPoint.getSignature().getModifiers()));

        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            logger.info("第{}个参数为:{}", (i + 1), args[i]);
        }
        logger.info("被代理的对象:{}", joinPoint.getTarget());
        logger.info("代理对象自己:{}", joinPoint.getThis());

        //执行后置方法
        doAfter(joinPoint);
    }


    /**
     * 环绕方法,可自定义目标方法执行的时机
     *
     * @param pjd JoinPoint的子接口,添加了
     *            Object proceed() throws Throwable 执行目标方法
     *            Object proceed(Object[] var1) throws Throwable 传入的新的参数去执行目标方法
     *            两个方法
     * @return 此方法需要返回值, 返回值视为目标方法的返回值
     */
    @Around("pointCut()")
    public Object aroundMethod(ProceedingJoinPoint pjd) {
        Object result = null;

        try {
            //前置通知
            System.out.println("目标方法执行前...");
            //执行目标方法
            //result = pjd.proeed();
            //用新的参数值执行目标方法 比如：http://localhost:8080/log?name=legend 返回应该是：legend  实际返回newSpring
            result = pjd.proceed(new Object[]{"newSpring"});
            //返回通知
            logger.info("目标方法返回结果后...");
        } catch (Throwable e) {
            //异常通知
            logger.info("执行目标方法异常后...");
            throw new RuntimeException(e);
        }
        //后置通知
        logger.info("目标方法执行后...");

        return result;
    }


    /**
     * 执行方法后处理
     */
    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint) {
        logger.info("进入了后置方法......");
        logger.info("基于切面做的日志方式doAfter方法执行完。。。。");
    }

}
