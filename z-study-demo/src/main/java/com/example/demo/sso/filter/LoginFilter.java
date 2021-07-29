package com.example.demo.sso.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * sso认证拦截未登录的用户请求
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化请求...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        Boolean flag = (Boolean) session.getAttribute("isLogin");
        if (flag) {
            chain.doFilter(request, response);
            return;
        }

        //跳转至sso认证中心
        res.sendRedirect("sso-server-url-with-system-url");

        //请求附带token参数
        String token = req.getParameter("token");
        if (token != null) {
            //去sso认证中心去校验token
            //verify()方法使用httpClient实现
            boolean verifyResult = this.verify("sso-server-verify-url", token);
            if (!verifyResult) {
                res.sendRedirect("sso-server-url");
                return;
            }
            chain.doFilter(request, response);
        }
    }

    private boolean verify(String s, String token) {
        /*HttpPost httpPost = new HttpPost("sso-server-verify-url-with-token");
        HttpResponse httpResponse = httpClient.execute(httpPost);*/
        return false;
    }

    @Override
    public void destroy() {
        System.out.println("注销请求....");
    }
}
