package com.legend.springboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 日志注解方式的AOP切面
 * https://blog.csdn.net/weixin_34014277/article/details/91369520
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/11
 */
@Aspect
@Component
public class LogAnnotationAop {

    private static Logger logger = LoggerFactory.getLogger(LogAnnotationAop.class);

    /**
     * 切点
     * execution(* com.legend.springboot.controller.*.*(..))
     *
     * @return
     */
    @Pointcut("@annotation(com.legend.springboot.annotation.Log)")
    public void pointCut() {
    }

    /**
     * 执行之前处理
     *
     * @param joinPoint
     */
    @Before(value = "pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestUrl = request.getRequestURL().toString();
        logger.info("URL:{}", requestUrl);
        //执行后置方法
        doAfter(joinPoint);
    }

    /**
     * 执行方法后清除数据源设置
     */
    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("基于@Log注解方式处理日志,doAfter方法执行完。。。。");
    }

}
