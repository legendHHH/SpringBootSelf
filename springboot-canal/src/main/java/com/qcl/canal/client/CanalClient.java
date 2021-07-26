package com.qcl.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Canal客户端
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/26
 */
//@Component
public class CanalClient {

    Logger logger = LoggerFactory.getLogger(CanalClient.class);

    /**
     * sql队列
     */
    private Queue<String> SQL_QUEUE = new ConcurrentLinkedQueue<>();

    @Resource
    private DataSource dataSource;

    public void run() {
        System.out.println("hello");

        //master服务器地址,以及开启的端口号,实例名instance.properties文件中不修改这用这个默认的,用户名密码使用远程连接账号的,同前配置文件
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1", 1111), "example", "", "");
        int batchSize = 1000;

        try {
            while (true) {
                logger.info("-------------canal开始连接-------------");
                try {
                    connector.connect();
                    connector.subscribe(".*\\..*");
                    connector.rollback();
                } catch (Exception e) {
                    logger.info("-------------canal连接失败,五分钟后尝试重新连接-------------");
                    try {
                        Thread.sleep(300000);
                    } catch (InterruptedException e1) {
                        logger.error(e1.getMessage());
                    }
                }
                logger.info("-------------canal连接成功-------------");
                break;
            }

            while (true) {
                try {
                    //从master拉取数据batchSize条记录
                    Message message = connector.getWithoutAck(batchSize);
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        Thread.sleep(10000);
                    } else {
                        this.dataHandle(message.getEntries());
                    }

                    //提交ack确认
                    connector.ack(batchId);

                    //设置队列sql语句执行最大值
                    if (SQL_QUEUE.size() >= 1) {
                        //executeQueueSql();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("canal入库方法" + e.getMessage());
                }
            }
        } finally {
            connector.disconnect();
        }
    }

    /**
     * 数据处理
     *
     * @param entrys
     */
    private void dataHandle(List<Entry> entrys) throws InvalidProtocolBufferException {
        /*for (Entry entry : entrys) {
            if (CanalEntry.EntryType.ROWDATA == entry.getEntryType()) {
                RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
                EventType eventType = rowChange.getEventType();
                if (eventType == EventType.DELETE) {
                    saveDeleteSql(entry);
                } else if (eventType == EventType.UPDATE) {
                    saveUpdateSql(entry);
                } else if (eventType == EventType.INSERT) {
                    saveInsertSql(entry);
                }

            }
        }*/
    }
}
