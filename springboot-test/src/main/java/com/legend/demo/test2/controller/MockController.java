package com.legend.demo.test2.controller;

import com.legend.demo.test2.domain.User;
import com.legend.demo.test2.service.MockService;
import com.yomahub.tlog.core.annotation.TLogAspect;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * Mock测试控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/19
 */
@Controller
public class MockController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockService mockService;

    @TLogAspect({"str","user.name","user.id","user.name"})
    @GetMapping("/test")
    @ResponseBody
    public String method(String str, User user){
        LOGGER.info("hello：{}，user：{}", str, user);
        return user.toString();
    }

    @GetMapping(value = "/editUser")
    public String editItem(Integer id, Model model) {
        LOGGER.info("hello Tlog测试1");
        User item = mockService.getUserById(id);
        model.addAttribute("user", item);
        return "userEdit";
    }

    @GetMapping(value = "/getUser")
    @ResponseBody
    public User getUser(Integer id) {
        LOGGER.info("hello Tlog测试2");
        User user = mockService.getUserById(id);
        return user;
    }

    /**
     * 方法超时重试测试
     *
     * http://localhost:9909/retry?pageNo=2&pageSize=20
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/retry", method = RequestMethod.GET)
    @ResponseBody
    public String retryHttpClient(@RequestParam(required = false, defaultValue = "1", value = "pageNo") int page,
                        @RequestParam(required = false, defaultValue = "10", value = "pageSize") int limit) {
        System.out.println("page："+ page + "pageSize：" + limit);
        User user = mockService.testRetry(page);
        return "page："+ page + "    pageSize：" + limit +"  user:" + user.toString();
    }

    /**
     * 网络请求重试
     * http://localhost:9909/retry2
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/retry2", method = RequestMethod.GET)
    @ResponseBody
    public String httpClientDemo() throws IOException {
        System.out.println("进来了....");
        HttpPost httpPost = new HttpPost("http://127.0.0.1:9909/hello/show");
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000).setConnectTimeout(5000).build();
        httpPost.setConfig(requestConfig);

        CloseableHttpClient httpClient = getHttpClient();
        try {
            CloseableHttpResponse execute = httpClient.execute(httpPost);
            LOGGER.info(">> {}", execute.getStatusLine().getStatusCode());
        } catch (Exception e) {
            if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
                throw new RuntimeException("超时异常");
            }
            LOGGER.error(e.getMessage(), e);
        } finally {
            httpClient.close();
        }
        return null;
    }

    public static CloseableHttpClient getHttpClient() {
        //第二种方案
        /*HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

            @Override
            public boolean retryRequest(
                    IOException exception,
                    int executionCount,
                    HttpContext context) {
                if (executionCount >= 5) {
                    // Do not retry if over max retry count
                    System.out.println("重试次数：" + executionCount);
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }

        };
        CloseableHttpClient httpClient = HttpClients.custom()
                .setRetryHandler(myRetryHandler)
                .build();

        return httpClient;*/

        //第一种方案
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(10);
        cm.setDefaultMaxPerRoute(20);
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setRetryHandler(new StandardHttpRequestRetryHandler())
                .setConnectionManager(cm)
                .build();
        return httpClient;
    }
}
