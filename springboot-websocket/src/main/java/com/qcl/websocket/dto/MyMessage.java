package com.qcl.websocket.dto;

import lombok.Data;

/**
 * 消息的实体对象
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/23
 */
@Data
public class MyMessage {
    /**
     *
     */
    private String userId;

    /**
     * 消息内容
     */
    private String message;
}
