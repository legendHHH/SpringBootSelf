package com.qcl.webssh.domain;

import lombok.Data;

/**
 * webssh数据传输
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/18
 */
@Data
public class WebSSHData {

    /**
     * 正在执行的操作
     */
    private String operate;

    /**
     * 主机
     */
    private String host;

    /**
     * 端口号默认为22
     */
    private Integer port = 22;
    private String username;
    private String password;
    private String command = "";
}
