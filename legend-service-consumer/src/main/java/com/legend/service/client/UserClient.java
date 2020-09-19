package com.legend.service.client;

import com.legend.service.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign客户端
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/19
 */
@FeignClient(value = "service-provider") // 标注该类是一个feign接口
public interface UserClient {

    /**
     * 查询用户
     *
     * @param id
     * @return
     */
    @GetMapping("user/{id}")
    User queryById(@PathVariable("id") Long id);
}
