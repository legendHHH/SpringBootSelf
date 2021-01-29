package com.legend.springbootmybatis.holder;

import com.legend.springbootmybatis.enums.DBTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadLocal将数据源设置到每个线程上下文中
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/29
 */
public class DBContextHolder {
    private static final Logger logger = LoggerFactory.getLogger(DBContextHolder.class);

    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void master() {
        set(DBTypeEnum.MASTER);
        logger.info("切换到master");
    }

    public static void slave() {
        set(DBTypeEnum.SLAVE1);
        logger.info("切换到slave1");
    }
}
