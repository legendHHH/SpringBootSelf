package com.qcl.webssh.websocket;

import com.qcl.webssh.constant.Constants;
import com.qcl.webssh.service.WebSSHService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

/**
 * WebSocket处理器
 * WebSocket注解的onOpen,onMessage和onClose事件对应于WebSSHWebSocketHandler的afterConnectionEstablished(),handleTextMessage(),和afterConnectionClosed()方法。
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/18
 */
@Component
public class WebSSHWebSocketHandler implements WebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(WebSSHWebSocketHandler.class);

    @Autowired
    private WebSSHService webSSHService;

    /**
     * 用户连接上WebSocket的回调
     *
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("用户:{},连接WebSSH", webSocketSession.getAttributes().get(Constants.USER_UUID_KEY));
        //调用初始化连接
        webSSHService.initConnection(webSocketSession);
    }

    /**
     * 收到消息的回调
     * 主要实现方法，用于从浏览器接受消息，并且发送消息返回给服务器。
     *
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        if (webSocketMessage instanceof TextMessage) {
            logger.info("用户:{},发送命令:{}", webSocketSession.getAttributes().get(Constants.USER_UUID_KEY), webSocketMessage.toString());
            //调用service接收消息
            webSSHService.recvHandle(((TextMessage) webSocketMessage).getPayload(), webSocketSession);
        } else if (webSocketMessage instanceof BinaryMessage) {
            logger.info("BinaryMessage type");
        } else if (webSocketMessage instanceof PongMessage) {
            logger.info("PongMessage type");
        } else {
            logger.info("Unexpected WebSocket message type: {}", webSocketMessage);
        }
    }

    /**
     * 出现错误的回调
     *
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        logger.error("数据传输错误");
    }

    /**
     * 连接关闭的回调
     *
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("用户:{}断开webssh连接", webSocketSession.getAttributes().get(Constants.USER_UUID_KEY));
        //调用service关闭连接
        webSSHService.close(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
