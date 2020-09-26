package com.qcl.permission.common;


import com.qcl.permission.model.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Request请求
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/26
 */
public class RequestHolder {

    /**
     * 当前线程
     */
    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }
}
