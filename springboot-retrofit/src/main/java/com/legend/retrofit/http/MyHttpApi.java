package com.legend.retrofit.http;

import com.github.lianjiatech.retrofit.spring.boot.annotation.Intercept;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.legend.retrofit.common.Result;
import com.legend.retrofit.entity.Person;
import com.legend.retrofit.interceptor.TimeStampInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * HTTP请求接口
 *
 * @Intercept配置表示：拦截HttpApi接口下/api/**路径下（排除/api/test/savePerson）的请求，拦截处理器使用TimeStampInterceptor。
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/2
 */
@Configuration
@RetrofitClient(baseUrl = "${test.baseUrl}")
//@Intercept(handler = TimeStampInterceptor.class, include = {"/api/**"}, exclude = "/api/test/savePerson")
public interface MyHttpApi {

    @GET("person")
    Result<Person> getPerson(@Query("id") Long id);

    @POST("savePerson")
    Result<Person> savePerson(@Body Person person);
}