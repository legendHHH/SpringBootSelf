package com.legend.retrofit.interceptor;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 扩展自定义拦截注解有以下2点需要注意：
 * 自定义拦截注解必须使用@InterceptMark标记。
 * 注解中必须包括include()、exclude()、handler()属性信息。
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/5
 */
public class SignInterceptor extends BasePathMatchInterceptor {

    /**
     * accessKeyId和accessKeySecret字段值会依据@Sign注解的accessKeyId()和accessKeySecret()值自动注入，
     * 如果@Sign指定的是占位符形式的字符串，则会取配置属性值进行注入。另外，accessKeyId和accessKeySecret字段
     * 必须提供setter方法。
     */
    private String accessKeyId;

    private String accessKeySecret;

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    @Override
    public Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newReq = request.newBuilder()
                .addHeader("accessKeyId", accessKeyId)
                .addHeader("accessKeySecret", accessKeySecret)
                .build();
        return chain.proceed(newReq);
    }
}
