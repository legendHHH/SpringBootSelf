package com.legend.minio.result;

/**
 * 封装API的错误码
 *
 * @author legend
 */
public interface IErrorCode {
    /**
     * 获取code
     *
     * @return
     */
    long getCode();

    /**
     * 获取消息
     *
     * @return
     */
    String getMessage();
}