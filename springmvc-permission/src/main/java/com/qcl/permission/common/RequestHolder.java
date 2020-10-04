package com.qcl.permission.common;


import com.qcl.permission.model.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Request请求(ThreadLocal 当前线程信息 高并发场景各自处理自己的信息)
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/26
 */
public class RequestHolder {

    /**
     * 当前线程---用户信息
     */
    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<>();

    /**
     * 当前线程---请求信息
     */
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    /**
     * 获取当前线程
     *
     * @return
     */
    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    /**
     * 进程结束之后需要移除 不然会造成内存泄漏
     */
    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }
}
