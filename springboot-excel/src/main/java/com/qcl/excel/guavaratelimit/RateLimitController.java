package com.qcl.excel.guavaratelimit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 限流测试控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/31
 */
@Controller
public class RateLimitController {

    @RequestLimiter(QPS = 5D, timeout = 200, timeunit = TimeUnit.MILLISECONDS, msg = "服务器繁忙,请稍后再试")
    @GetMapping("/test")
    @ResponseBody
    public Object test() {
        return Collections.singletonMap("success", "true");
    }
}
