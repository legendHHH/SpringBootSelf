package com.legend.springbootmybatis.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Properties;

/**
 * 自定义拦截器实现mybatis计算sql的耗时时间监听慢sql
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/3/27
 */
@Component
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "query",
                args = {Statement.class, ResultHandler.class})
})
public class CountSqlTimeInterceptor implements Interceptor {
    public static final Logger log = LoggerFactory.getLogger(CountSqlTimeInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Long b = System.currentTimeMillis();
        String sql = ((StatementHandler) invocation.getTarget()).getBoundSql().getSql();
        Object obj = invocation.proceed();
        log.info(sql + " cost " + (System.currentTimeMillis() - b) + "ms");
        return obj;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}