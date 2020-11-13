package com.legend.demo.test2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 第三个事务
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/13
 */
@Service
public class TransactionalService3 {

    private Logger log = LoggerFactory.getLogger(TransactionalService3.class);

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String txMethod3() {
        System.out.println("hello3");
        log.info("TransactionalService3当前事务名称：{}",TransactionSynchronizationManager.getCurrentTransactionName());
        return "第三个事务!";
    }
}
