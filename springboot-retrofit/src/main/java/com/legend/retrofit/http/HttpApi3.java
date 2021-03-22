package com.legend.retrofit.http;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.legend.retrofit.common.Result;
import com.legend.retrofit.entity.Person;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.concurrent.CompletableFuture;

/**
 * HTTP请求接口
 * 自定义配置的CallAdapter.Factory优先级更高！
 *
 * @author legend
 * @version 1.0
 * @Intercept配置表示：拦截HttpApi接口下/api/**路径下（排除/api/test/savePerson）的请求，拦截处理器使用TimeStampInterceptor。
 * @description
 * @date 2021/03/22
 */
@Component("httpApi3")
@RetrofitClient(baseUrl = "${test.baseUrl}", poolName = "test1")
public interface HttpApi3 {

    /**
     * Call<T>
     * 不执行适配处理，直接返回Call<T>对象
     *
     * @param id
     * @return
     */
    @GET("person")
    Call<Result<Person>> getPersonCall(@Query("id") Long id);

    /**
     * CompletableFuture<T>
     * 将响应体内容适配成CompletableFuture<T>对象返回
     *
     * @param id
     * @return
     */
    @GET("person")
    CompletableFuture<Result<Person>> getPersonCompletableFuture(@Query("id") Long id);

    /**
     * Void
     * 不关注返回类型可以使用Void。如果http状态码不是2xx，直接抛错！
     *
     * @param id
     * @return
     */
    @GET("person")
    Void getPersonVoid(@Query("id") Long id);

    /**
     * Response<T>
     * 将响应内容适配成Response<T>对象返回
     *
     * @param id
     * @return
     */
    @GET("person")
    Response<Result<Person>> getPersonResponse(@Query("id") Long id);

    /**
     * 其他任意Java类型
     * 将响应体内容适配成一个对应的Java类型对象返回，如果http状态码不是2xx，直接抛错！
     *
     * @param id
     * @return
     */
    @GET("person")
    Result<Person> getPerson(@Query("id") Long id);
}