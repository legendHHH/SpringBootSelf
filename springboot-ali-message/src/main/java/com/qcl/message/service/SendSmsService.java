package com.qcl.message.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 阿里云Service
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/23
 */
@Service
public class SendSmsService {
    /**
     * 短信API产品名称（短信产品名固定，无需修改）
     */
    private static final String product = "Dysmsapi";

    /**
     * 短信API产品域名，接口地址固定，无需修改
     */
    private static final String domain = "dysmsapi.aliyuncs.com";

    /**
     * 此处需要替换成开发者自己的accessKeyId和accessKeySecret(在阿里云访问控制台寻找)
     */
    private static final String accessKeyId = "你的accessKeyId "; //TODO: 这里要写成你自己生成的
    private static final String accessKeySecret = "你的accessKeySecret ";//TODO: 这里要写成你自己生成的

    /**
     * 短信发送
     *
     * @param phone
     * @return
     * @throws ClientException
     */
    public SendSmsResponse sendSms(String phone) throws ClientException {
        String code = getMsgCode();
        /* 超时时间，可自主调整 */
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        /* 初始化acsClient,暂不支持region化 */
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        /* 组装请求对象-具体描述见控制台-文档部分内容 */
        SendSmsRequest request = new SendSmsRequest();
        /* 必填:待发送手机号 */
        request.setPhoneNumbers(phone);
        /* 必填:短信签名-可在短信控制台中找到 */
        request.setSignName("浪漫纪"); //TODO: 这里是你短信签名
        /* 必填:短信模板code-可在短信控制台中找到 */
        request.setTemplateCode("SMS_164750191"); //TODO: 这里是你的模板code
        /* 可选:模板中的变量替换JSON串,如模板内容为"亲爱的用户,您的验证码为${code}"时,此处的值为 */
        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        System.out.println("给电话为" + phone + "发送的验证码为：" + code);

        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            System.out.println("短信发送成功！验证码：" + code);
        } else {
            System.out.println("短信发送失败！");
        }
        return sendSmsResponse;
    }

    /**
     * 随机生成验证码
     *
     * @return
     */
    private static String getMsgCode() {
        int n = 6;
        StringBuilder code = new StringBuilder();
        Random ran = new Random();
        for (int i = 0; i < n; i++) {
            code.append(Integer.valueOf(ran.nextInt(10)).toString());
        }
        return code.toString();
    }
}