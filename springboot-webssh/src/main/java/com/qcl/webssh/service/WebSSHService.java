package com.qcl.webssh.service;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * service
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/18
 */
public interface WebSSHService {

    /**
     * 初始化ssh连接
     *
     * @param session
     */
    public void initConnection(WebSocketSession session);

    /**
     * 处理客户端发送的数据
     *
     * @param buffer
     * @param session
     */
    public void recvHandle(String buffer, WebSocketSession session);

    /**
     * 数据写回前端 for websocket
     *
     * @param session
     * @param buffer
     * @throws IOException
     */
    public void sendMessage(WebSocketSession session, byte[] buffer) throws IOException;

    /**
     * @param session
     */
    public void close(WebSocketSession session);
}
