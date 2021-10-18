package com.legend.springbootredis.interceptor;

import com.alibaba.fastjson.JSON;
import com.legend.springbootredis.annoation.AccessLimit;
import com.legend.springbootredis.constant.CodeMsg;
import com.legend.springbootredis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * 接口防刷拦截器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/30
 */
//@Component
public class BrushInterfaceInterceptor extends HandlerInterceptorAdapter {

    //@Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*//判断请求是否属于方法的请求
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                //没有则直接放行
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean login = accessLimit.needLogin();
            String key = request.getRequestURI();
            //如果需要登录
            if (login) {
                //TODO 获取登录的session进行判断
                //.....

                //TODO 这里假设用户是1,项目中是动态获取的userId
                key += "" + "1";
            }

            //从redis中获取用户访问的次数
            AccessKey ak = AccessKey.withExpire(seconds);
            Integer count = redisService.get(ak, key, Integer.class);
            if (count == null) {
                //第一次访问
                redisService.set(ak, key, 1);
            } else if (count < maxCount) {
                //加1
                redisService.incr(ak, key);
            } else {
                //超出访问次数
                // 这里的CodeMsg是一个返回参数
                this.render(response, CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }

        }*/

        return true;
    }

    private void render(HttpServletResponse response, CodeMsg cm) throws Exception {
        /*response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.error(cm));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();*/
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
