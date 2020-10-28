package com.qcl.message.controller;

import com.qcl.message.config.AliyunSmsSendProperty;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/10/26
 */
@Controller
public class TestController {

    @GetMapping("/hbrm/log/download")
    @ResponseBody
    public String downloadLog(HttpServletResponse response, @RequestParam("date") String date) {
        System.out.println(date);
        return "true:/hbrm/log/download";
    }

    @GetMapping("/hbrm/log/download/{date}")
    @ResponseBody
    public String downloadLog2(HttpServletResponse response, @PathVariable("date") String date) {
        System.out.println(date);
        return "true:/hbrm/log/download/{date}";
    }

    public static void main(String[] args) {
        List<AliyunSmsSendProperty> aliyunSmsSendPropertyArrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AliyunSmsSendProperty aliyunSmsSendProperty = new AliyunSmsSendProperty();
            aliyunSmsSendProperty.setAccessKeyId("66"+ i);
            aliyunSmsSendPropertyArrayList.add(aliyunSmsSendProperty);
        }
        aliyunSmsSendPropertyArrayList.forEach(ali ->{
            ali.setSingleName("singleName");
        });

        System.out.println(aliyunSmsSendPropertyArrayList.toString());

    }
}
