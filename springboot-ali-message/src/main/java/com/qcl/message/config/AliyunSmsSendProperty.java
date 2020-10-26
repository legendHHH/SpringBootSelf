package com.qcl.message.config;

import org.springframework.stereotype.Component;

/**
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/10/23
 */
@Component
public class AliyunSmsSendProperty {

    private String accessKeyId;
    private String accessSecret;
    private String singleName;
    private String templateCode;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getSingleName() {
        return singleName;
    }

    public void setSingleName(String singleName) {
        this.singleName = singleName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public AliyunSmsSendProperty(String accessKeyId, String accessSecret, String singleName, String templateCode) {
        this.accessKeyId = accessKeyId;
        this.accessSecret = accessSecret;
        this.singleName = singleName;
        this.templateCode = templateCode;
    }

    public AliyunSmsSendProperty() {
    }
}
