package com.legend.demo.test2.service;

import com.legend.demo.test2.domain.User;
import org.springframework.stereotype.Service;

/**
 * Mock实现类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/19
 */
@Service
public class MockServiceImpl implements MockService {

    @Override
    public User getUserById(Integer id) {
        return new User(28, "zhangsan");
    }
}
