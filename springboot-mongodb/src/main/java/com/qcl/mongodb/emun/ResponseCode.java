package com.qcl.mongodb.emun;

/**
 * 响应编码
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/28
 */
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(200, "success"),
    /**
     * 保存成功
     */
    SAVE_SUCCESS(200, "保存成功"),
    /**
     * 保存失败
     */
    SAVE_ERROR(500, "保存失败"),
    /**
     * 删除成功
     */
    DELETE_SUCCESS(200, "删除成功"),
    /**
     * 删除失败
     */
    DELETE_ERROR(500, "删除失败"),
    /**
     * 获取数据库连接失败
     */
    NOT_FIND_DATABASE_CONN(401, "not find database conn"),
    LOGIN_SUCCESS(200, "登陆成功"),
    LOGIN_ERROR(401, "登陆失败"),
    LOGIN_EASY(500, "登陆密码过于简单"),
    PARAMETER_ERROR(403, "参数错误"),
    SAMPLE_STOCK_HANDOVER_ERROR(400, "此样衣正在转交中"),
    STOCKOUT_ERROR(400, "库存不足，请修改后提交");

    private final int code;
    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}