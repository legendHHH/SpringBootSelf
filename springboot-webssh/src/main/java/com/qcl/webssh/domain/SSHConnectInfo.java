package com.qcl.webssh.domain;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

/**
 * ssh连接信息
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/18
 */
@Data
public class SSHConnectInfo {

    private WebSocketSession webSocketSession;

    /**
     * 服务器远程操作
     */
    private JSch jSch;
    private Channel channel;
}
