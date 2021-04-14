package com.legend.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * 创建es客户端
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/13
 */
public class EsTest_Client {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //...业务逻辑操作

        //关闭客户端
        esClient.close();
    }
}
