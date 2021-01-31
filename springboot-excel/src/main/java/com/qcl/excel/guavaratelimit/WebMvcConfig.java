package com.qcl.excel.guavaratelimit;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2021/1/31
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RequestLimiterInterceptor requestLimiterInterceptor;

    public WebMvcConfig() {
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 请求限流
        registry.addInterceptor(requestLimiterInterceptor).addPathPatterns("/**");

        //test接口，1秒钟生成1个令牌，也就是1秒中允许一个人访问
        /*registry.addInterceptor(new RequestLimiterInterceptor(RateLimiter.create(1, 1, TimeUnit.SECONDS)))
                .addPathPatterns("/test");*/
    }
}
