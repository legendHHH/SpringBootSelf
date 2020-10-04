package com.qcl.permission.filter;

import com.qcl.permission.common.RequestHolder;
import com.qcl.permission.model.SysUser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录请求拦截
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/4
 */
@Slf4j
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LoginFilter初始化init.....");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("LoginFilter拦截操作doFilter.....");
        //强转request和response对象
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        //获取登陆用户
        SysUser user = (SysUser) httpServletRequest.getSession().getAttribute("user");

        if (user == null) {
            //未登录  后台强制跳转登录
            //当前路径url进行跳转
            String path = "/signin.jsp";
            httpServletResponse.sendRedirect(path);
            return;
        }
        //将当前线程进行绑定操作
        RequestHolder.add(user);
        RequestHolder.add(httpServletRequest);

        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }

    @Override
    public void destroy() {
        log.info("LoginFilter销毁操作destroy.....");
    }
}
