package com.legend.es.utils;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * es客户端RestHighLevelClient工具类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2023/1/9
 */
public class EsClientUtil {

    private static final String DEFAULT_SCHEME_NAME = HttpHost.DEFAULT_SCHEME_NAME;

    /**
     * 默认的RestHighLevelClient
     *
     * @param hostName
     * @param port
     * @return
     */
    public static RestHighLevelClient getDefaultRestHighLevelClient(String hostName, Integer port) {
        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(RestClient.builder(new HttpHost(hostName, port, DEFAULT_SCHEME_NAME)));
        return esClient;
    }


    /**
     * RestHighLevelClient带用户名和密码
     *
     * @param hostName
     * @param port
     * @param userName
     * @param password
     * @return
     */
    public static RestHighLevelClient getRestHighLevelClientWithUserNameAndPassword(String hostName, Integer port, String userName, String password) {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                //es账号密码（默认用户名为elastic）
                new UsernamePasswordCredentials(userName, password));

        //创建带用户名密码的es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost(hostName, port, DEFAULT_SCHEME_NAME)).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        httpClientBuilder.disableAuthCaching();
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                })
        );
        return esClient;
    }
}
