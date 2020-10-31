package com.qcl.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * kafka监听
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/1
 */
@Slf4j
//@Component
public class RawDataListener {

    @Value("${kafka_api_monitor_env}")
    private String servers;

    @KafkaListener(topics = "test2", groupId = "consumer-group1")
    public void listen(ConsumerRecord<?, ?> record) {
        String value = (String) record.value();
        String topic = record.topic();
        log.info("topic:{},接收到的消息为:{}", topic, value);
        //转化成class对象操作
    }
}
