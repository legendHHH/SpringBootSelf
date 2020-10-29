package com.qcl.mongodb.response;

import com.qcl.mongodb.constants.Constant;
import com.qcl.mongodb.emun.ResponseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 响应
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/28
 */
@ApiModel
@Data
public class Response<T> {

    /**
     * 返回码
     */
    @ApiModelProperty(position = 1, value = "返回码")
    private int code;

    /**
     * 返回文字内容
     */
    @ApiModelProperty(position = 2, value = "返回信息")
    private String message;

    /**
     * 返回实体对象
     */
    @ApiModelProperty(position = 3, value = "返回体")
    private T obj;

    public Response(int code, String message, T obj) {
        this.code = code;
        this.message = message;
        this.obj = obj;
    }

    public Response() {
    }

    public Response(Exception ex) {
        this.code = Constant.EXCEPTION;
        this.message = ex.getMessage();
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMsg();
    }

    public Response(ResponseCode responseCode, T obj) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMsg();
        this.obj = obj;
    }
}