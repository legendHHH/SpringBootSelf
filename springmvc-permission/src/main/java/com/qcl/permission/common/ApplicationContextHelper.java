package com.qcl.permission.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring上下文工具
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/26
 */
@Component("applicationContextHelper")
public class ApplicationContextHelper implements ApplicationContextAware {

    /**
     * 全局的 applicationContext
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    /**
     * 从applicationContext上下文获取Bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T popBean(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    public static <T> T popBean(String name, Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name, clazz);
    }
}
