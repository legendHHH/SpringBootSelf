package com.qcl.message.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.qcl.message.service.SendSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 阿里云Controller
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/23
 */
@RestController
public class SendSmsController {

    @Autowired
    private SendSmsService sendSmsService;

    /**
     * 发送短信
     * http://localhost:8080/sendCode?phone=手机号
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "/sendCode")
    public Object sendCode(String phone) {
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = sendSmsService.sendSms(phone);
        } catch (Exception e) {
            return "发送失败！";
        }
        return sendSmsResponse;
    }

}