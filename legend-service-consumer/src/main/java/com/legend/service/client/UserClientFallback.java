package com.legend.service.client;

import com.legend.service.pojo.User;
import org.springframework.stereotype.Component;

/**
 * Feign降级类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2019/11/2
 */
@Component
public class UserClientFallback implements UserClient {

    @Override
    public User queryById(Long id) {
        User user = new User();
        user.setUserName("服务器繁忙，请稍后再试！");
        return user;
    }
}
