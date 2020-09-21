package com.qcl.shiro.domain;

/**
 * 权限实体类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/21
 */
public class Permission {

    /**
     * 权限id
     */
    private Integer pid;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限路径
     */
    private String url;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
