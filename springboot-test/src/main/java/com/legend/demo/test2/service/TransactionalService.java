package com.legend.demo.test2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Map;

/**
 * 第一个事务
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/13
 */
@Service
public class TransactionalService {

    private Logger log = LoggerFactory.getLogger(TransactionalService.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionalService2 transactionalService2;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String txMethod() {
        System.out.println("hello1");
        String txMethod2 = transactionalService2.txMethod2();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT TRX_ID FROM INFORMATION_SCHEMA.INNODB_TRX WHERE TRX_MYSQL_THREAD_ID = CONNECTION_ID( );");
        log.info("maps：{}" ,maps);
        log.info("TransactionalService当前事务名称：{}",TransactionSynchronizationManager.getCurrentTransactionName());
        return "第一个事务," + txMethod2;
    }
}
