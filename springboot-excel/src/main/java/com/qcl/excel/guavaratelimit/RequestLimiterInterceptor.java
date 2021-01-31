package com.qcl.excel.guavaratelimit;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求限流拦截器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/31
 */
@Component
public class RequestLimiterInterceptor extends HandlerInterceptorAdapter {

    private Logger log = LoggerFactory.getLogger(RequestLimiterInterceptor.class);

    /**
     * 不同的方法存放不同的令牌桶
     */
    private final Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String msg = "";
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod hm = (HandlerMethod) handler;
                RequestLimiter requestLimit = hm.getMethodAnnotation(RequestLimiter.class);
                //判断是否有注解
                if (requestLimit != null) {
                    msg = requestLimit.msg();
                    log.warn("msg:{}", msg);
                    //获取请求的url
                    String url = request.getRequestURI();
                    log.warn("请求url:{}", url);
                    RateLimiter rateLimiter;
                    // 判断map集合中是否有创建好的令牌桶
                    if (!rateLimiterMap.containsKey(url)) {
                        // 创建令牌桶,以n r/s往桶中放入令牌
                        rateLimiter = RateLimiter.create(requestLimit.QPS());
                        rateLimiterMap.put(url, rateLimiter);
                    }
                    rateLimiter = rateLimiterMap.get(url);

                    // 获取令牌
                    boolean acquire = rateLimiter.tryAcquire(requestLimit.timeout(), requestLimit.timeunit());
                    if (acquire) {
                        //获取令牌成功
                        return super.preHandle(request, response, handler);
                    } else {
                        log.warn("请求被限流,url:{}", request.getServletPath());
                        //获取失败，直接响应“错误信息” 也可以通过抛出异常，通过全全局异常处理器响应客户端
                        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                        response.getWriter().write(msg == null ? "服务器繁忙，，，" : msg);
                        return false;
                    }

                }
            }
            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.getWriter().write("对不起,请求似乎出现了一些问题,请您稍后重试！");
            return false;
        }
    }
}
