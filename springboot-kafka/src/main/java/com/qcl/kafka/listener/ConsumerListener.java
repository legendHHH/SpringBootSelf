package com.qcl.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 消费者监听器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/31
 */
@Component
@Slf4j
public class ConsumerListener {

    /**
     * 我们的消费者监听器才用的并发批量下拉数据 才用手动提交方式避免消息丢失
     *
     * @param list
     * @param ack
     */
    @KafkaListener(topics = "test", groupId = "consumer-group")
    public void listen(List<String> list, Acknowledgment ack) {
        log.info("本次批量拉取数量:" + list.size() + " 开始消费....");
        List<String> msgList = new ArrayList<>();
        for (String record : msgList) {
            Optional<?> kafkaMessage = Optional.ofNullable(record);
            kafkaMessage.ifPresent(o -> {
                msgList.add(o.toString());
            });
        }

        if (msgList.size() > 0) {
            for (String msg : msgList) {
                log.info("开始消费消息【" + msg + "】");
            }
            // 更新索引
            // updateES(messages);
        }

        //手动提交offset
        ack.acknowledge();
        msgList.clear();
        log.info("消费结束");
    }

    /**
     * 第二个监听器
     * topic一定要现在kafka中创建不然会报错：Topic(s) [test2] is/are not present and missingTopicsFatal is true
     *
     * @param record
     * @param ack
     * @param topic
     */
    //@KafkaListener(topics = "test2", groupId = "consumer-group1")
    public void topic_test1(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("topic_test1 消费了： Topic:" + topic + ",Message:" + msg);
            ack.acknowledge();
        }
    }
}
