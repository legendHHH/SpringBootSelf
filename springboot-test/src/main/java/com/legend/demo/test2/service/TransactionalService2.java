package com.legend.demo.test2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 第二个事务
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/13
 */
@Service
public class TransactionalService2 {

    private Logger log = LoggerFactory.getLogger(TransactionalService2.class);

    @Autowired
    private TransactionalService3 transactionalService3;

    public String txMethod2() {
        System.out.println("hello2");
        String txMethod3 = transactionalService3.txMethod3();
        log.info("TransactionalService2当前事务名称：{}", TransactionSynchronizationManager.getCurrentTransactionName());
        return "第二个事务," + txMethod3;
    }
}
