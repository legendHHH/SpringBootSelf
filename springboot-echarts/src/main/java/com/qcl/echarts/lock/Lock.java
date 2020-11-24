package com.qcl.echarts.lock;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/22
 */
public interface Lock {

    /**
     * 获取锁
     */
    void getLock() throws Exception;

    /**
     * 释放锁
     */
    void unlock() throws Exception;
}
