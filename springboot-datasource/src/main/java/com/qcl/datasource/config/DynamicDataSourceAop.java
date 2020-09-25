package com.qcl.datasource.config;

import com.qcl.datasource.bean.AreaKeyId;
import org.aopalliance.intercept.Joinpoint;
import org.apache.ibatis.session.SqlSessionFactory;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 动态数据源Aop切面
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/11
 */
//@Aspect
//@Component
@Deprecated
public class DynamicDataSourceAop {


    /**
     * 切点
     *
     * @return
     */
    //@Pointcut("execution(* com.qcl.datasource.controller.*.*(..))")
    public void pointCut() {
    }

    /**
     * 执行之前更换数据源
     *
     * @param joinpoint
     */
    //@Before("pointCut()")
    public void doBefore(Joinpoint joinpoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String area = request.getHeader("area");
        System.out.println("area:" + area);
        if (AreaKeyId.BEIJING.getArea().equals(area)) {
            DynamicDataSourceContextHolder.set(AreaKeyId.BEIJING);
        } else if (AreaKeyId.CHENGDU.getArea().equals(area)) {
            DynamicDataSourceContextHolder.set(AreaKeyId.CHENGDU);
        }
    }

    /**
     * 执行方法后清除数据源设置
     */
    //@After("pointCut()")
    public void doAfter(Joinpoint joinpoint) {
        DynamicDataSourceContextHolder.clear();
    }

}
