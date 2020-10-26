package com.qcl.message.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.qcl.message.config.AliyunSmsSendProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 阿里云工具类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/23
 */
@Component
public class AliyunSmsUtils {
    Logger logger = LoggerFactory.getLogger(AliyunSmsUtils.class);

    @Autowired
    private AliyunSmsSendProperty aliyunSmsSendProperty;

    /**
     * 发送短信
     *
     * @param phone
     * @param code
     * @return
     */
    public String sendMessage(String phone, String code) {
        logger.info(aliyunSmsSendProperty.getAccessKeyId());
        logger.info(aliyunSmsSendProperty.getAccessSecret());
        logger.info(aliyunSmsSendProperty.getSingleName());
        logger.info(aliyunSmsSendProperty.getTemplateCode());
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunSmsSendProperty.getAccessKeyId(), aliyunSmsSendProperty.getAccessSecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();

        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", aliyunSmsSendProperty.getSingleName());
        request.putQueryParameter("TemplateCode", aliyunSmsSendProperty.getTemplateCode());
        request.putQueryParameter("TemplateParam", "{\"code\":" + code + "}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}