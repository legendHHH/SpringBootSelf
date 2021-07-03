package com.legend.spring.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Windows环境条件
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/3
 */
public class WindowsCondition implements Condition {

    /**
     * @param context  判断条件能使用的上下文(环境)
     * @param metadata 注释信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String property = environment.getProperty("os.name");
        if (property.contains("Windows")) {
            System.out.println("matches windows");
            return true;
        }
        return false;
    }
}
