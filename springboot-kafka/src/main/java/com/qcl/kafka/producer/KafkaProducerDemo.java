package com.qcl.kafka.producer;

import com.qcl.kafka.utils.GsonUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 生产者发送消息Main函数测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/9/4
 */
public class KafkaProducerDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties kafkaPropertie = new Properties();
        //配置broker地址，配置多个容错
        kafkaPropertie.put("bootstrap.servers", "localhost:9092");
        //配置key-value允许使用参数化类型
        kafkaPropertie.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaPropertie.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer kafkaProducer = new KafkaProducer(kafkaPropertie);

        SortedMap<String, Object> params = new TreeMap<String, Object>();
        //测试定时任务测试
        params.put("placepointid", "-9");
        params.put("posKey", "syincapos");
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("gdpull", GsonUtil.GsonString(params));

        Future send = kafkaProducer.send(record);
        System.out.println(send.isDone());
        System.out.println(send.get().toString());
    }
}