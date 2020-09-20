package com.qcl.mybatisplus.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 请求响应
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/20
 */
public class ResponseDataVO {

    /**
     * 返回状态编码
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;

    /**
     * 返回信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    /**
     * 数据
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<?> rows;

    /**
     * 成功标识
     */
    private Boolean success;

    /**
     * 总数
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long total;

    public ResponseDataVO() {
    }

    public ResponseDataVO(boolean success) {
        setSuccess(success);
    }

    public ResponseDataVO(List<?> list) {
        this(true);
        setRows(list);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<?> getRows() {
        return rows;
    }

    public Long getTotal() {
        return total;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
        try {
            boolean isPage = "com.github.pagehelper.Page".equals(rows.getClass().getCanonicalName());
            if (isPage) {
                setTotal((Long) rows.getClass().getDeclaredMethod("getTotal").invoke(rows));
            } else {
                setTotal((long) rows.size());
            }
        } catch (Exception e) {
            throw new RuntimeException("Get page total failed!", e);
        }
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ResponseDataVO{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                ", success=" + success +
                ", total=" + total +
                '}';
    }
}
