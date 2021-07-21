package com.legend.demo.test2.service;

import com.legend.demo.test2.domain.User;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
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

    /**
     * 正常方法：value=RuntimeException===>捕获异常类,关键元素
     *
     * @param id
     * @return
     */
    @Override
    @Retryable(value = RuntimeException.class, maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    public User testRetry(Integer id) {
        System.out.println("进来了：" + (id++));
        id++;
        if (id < 0) {
            throw new RuntimeException("idd不能小于0");
        }
        return new User(28222, id + "zhangsan1111");
    }

    //错误,方法返回值不一致：Cannot locate recovery method; nested exception is java.lang.RuntimeException: idd不能小于0] with root cause
    /*@Recover
    public String recover(RuntimeException e, Integer id){
        System.out.println("异常恢复方法接受的参数：" + id);
        System.out.println("recovery方法生效hello-===>" + e.getMessage());
        return "异常之后超时重试兜底方法生效";
    }*/

    @Recover
    public User recover(RuntimeException e, Integer id){
        System.out.println("异常恢复方法接受的参数：" + id);
        System.out.println("recovery方法生效hello-===>" + e.getMessage());
        return new User(88888, "异常之后超时重试兜底方法生效");
    }
}
