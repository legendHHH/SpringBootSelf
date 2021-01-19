package com.qcl.webssh.config;

import com.qcl.webssh.interceptor.WebSocketInterceptor;
import com.qcl.webssh.websocket.WebSSHWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket配置
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/18
 */
@Configuration
@EnableWebSocket
public class WebSSHWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSSHWebSocketHandler webSSHWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //socket通道
        //向spring容器注册一个handler地址，我把他理解成requestMapping
        webSocketHandlerRegistry.addHandler(webSSHWebSocketHandler, "/webssh")
                .addInterceptors(new WebSocketInterceptor())
                //跨域设置，*表示所有域名都可以，不限制， 域包括ip：port, 指定*可以是任意的域名，不加的话默认localhost+本服务端口
                .setAllowedOrigins("*");
    }
}
