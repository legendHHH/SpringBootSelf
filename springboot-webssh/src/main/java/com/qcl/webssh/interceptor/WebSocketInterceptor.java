package com.qcl.webssh.interceptor;

import com.qcl.webssh.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.UUID;

/**
 * WebSocket拦截器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/18
 */
@Slf4j
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

    /**
     * Handler处理前调用
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
            map.put(Constants.USER_REQUEST_KEY, request);

            //生成一个UUID
            String uuid = UUID.randomUUID().toString().replace("-", "");
            //将uuid放到websocketsession中
            map.put(Constants.USER_UUID_KEY, uuid);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        log.info("afterHandshake.....");
    }
}
