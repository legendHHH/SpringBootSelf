package com.qcl.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生产者controller
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/31
 */
@RestController
@Slf4j
public class ProducerController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/send/{message}")
    public String sendMsg(@PathVariable("message") String message) {
        //建议看一下KafkaTemplate的源码 很多api 我们可以指定分区发送消息
        //使用kafka模板发送信息
        kafkaTemplate.send("test", message);
        String res = "消息:【" + message + "】发送成功 SUCCESS !";
        log.info(res);
        return res;
    }

    @GetMapping("/send2/{message}")
    public void queryKafkaMessage(String message) {
        ListenableFuture<SendResult<String, Object>> listenableFuture = kafkaTemplate.send("test", message);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //发送失败的处理
                log.info("topic.test" + " - 生产者 发送消息失败：" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                //成功的处理
                log.info("topic.test" + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
            }

        });
    }
}
