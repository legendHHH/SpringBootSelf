package com.legend.mybatis.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

/**
 * 自定义插件，MyBatis所允许拦截的方法如下：
 * 执⾏器Executor
 * SQL语法构建器StatementHandler
 * 参数处理器ParameterHandler
 * 结果集处理器ResultSetHandler
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/10
 */
@Intercepts({
        @Signature(
                //这是指拦截哪个接口
                type = StatementHandler.class,
                //这个接⼝内的哪个方法名
                method = "prepare",
                // 这是拦截的⽅法的⼊参，按顺序写到这，不要多也不要少，如果方法重载，可是要通过方法名和⼊参来确定唯一的
                args = {Connection.class, Integer.class})
})
public class MyPlugin implements Interceptor {

    /**
     * 拦截方法：只要被拦截的目标对象的目标方法被执行时，每次都会执行intercept方法
     * 增强的逻辑写在这里。比如分页、监控
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("对方法进行了增强");
        //原方法执行
        return invocation.proceed();
    }

    /**
     * 主要为了把当前的拦截器生成代理对象存到拦截器链中
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        System.out.println("将要包装的目标对象："+target);
        Object wrap = Plugin.wrap(target, this);
        return wrap;
    }

    /**
     * 插件初始化的时候调用，也只调用一次，插件配置的属性从这里设置进来
     * 获取配置文件的参数
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("获取到的配置文件参数是：" + properties);

    }
}
