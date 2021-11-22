package com.legend.springbootmybatis.aop;

import com.legend.springbootmybatis.holder.DBContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 多数据源AOP切面配置
 * 指定使用的是主库还是从库
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/11/22
 */
@Aspect
@Component
public class DataSourceAop {

    private Logger logger = LoggerFactory.getLogger(DataSourceAop.class);

    @Pointcut("!@annotation(com.legend.springbootmybatis.annotation.MyMysqlMaster) " +
            "&& (execution(* com.legend.springbootmybatis.service..*.select*(..)) " +
            "|| execution(* com.legend.springbootmybatis.service..*.get*(..)) " +
            "|| execution(* com.legend.springbootmybatis.service..*.find*(..)))")
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.legend.springbootmybatis.annotation.MyMysqlMaster) " +
            "|| execution(* com.legend.springbootmybatis.service..*.insert*(..)) " +
            "|| execution(* com.legend.springbootmybatis.service..*.add*(..)) " +
            "|| execution(* com.legend.springbootmybatis.service..*.update*(..)) " +
            "|| execution(* com.legend.springbootmybatis.service..*.edit*(..)) " +
            "|| execution(* com.legend.springbootmybatis.service..*.delete*(..)) " +
            "|| execution(* com.legend.springbootmybatis.service..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        logger.info("正在进行读操作,正在选择使用从库操作......");
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        logger.info("正在进行写操作,正在选择使用主库操作......");
        DBContextHolder.master();
    }

    /**
     * 判断哪些需要读从数据库，其余的走主数据库
     * 另一种写法：if...else...
     *
     * @param jp
     */
    //@Before("execution(* com.legend.springbootmybatis.service.impl.*.*(..))")
    public void before(JoinPoint jp) {
        String methodName = jp.getSignature().getName();

        if (StringUtils.startsWithAny(methodName, "get", "select","query", "find")) {
            DBContextHolder.slave();
        } else {
            DBContextHolder.master();
        }
    }
}