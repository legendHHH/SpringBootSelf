package com.legend.retrofit.http;

import com.github.lianjiatech.retrofit.spring.boot.annotation.Intercept;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.legend.retrofit.annoation.Sign;
import com.legend.retrofit.common.Result;
import com.legend.retrofit.entity.Person;
import com.legend.retrofit.interceptor.TimeStampInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * @date 2021/03/22
 */
@Component("httpApi2")
@RetrofitClient(baseUrl = "${test.baseUrl}",poolName = "test1")
@Sign(accessKeyId = "${test.accessKeyId}", accessKeySecret = "${test.accessKeySecret}", exclude = {"/api/test/person"})
public interface HttpApi2 {

    @GET("person")
    Result<Person> getPerson(@Query("id") Long id);

    @POST("savePerson")
    Result<Person> savePerson(@Body Person person);
}