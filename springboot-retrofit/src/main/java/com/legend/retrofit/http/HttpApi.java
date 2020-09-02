package com.legend.retrofit.http;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.legend.retrofit.entity.Person;
import retrofit2.http.GET;
import retrofit2.http.Query;

@RetrofitClient(baseUrl = "${test.baseUrl}")
public interface HttpApi {

//    @GET("person")
//    Result<Person> getPerson(@Query("id") Long id);
}