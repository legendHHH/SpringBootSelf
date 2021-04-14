package com.legend.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

/**
 * 索引创建
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/13
 */
public class EsTest_Client_Index_Create {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //创建索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("user2");
        CreateIndexResponse response = esClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println("索引操作：" + response.isAcknowledged());

        //关闭客户端
        esClient.close();
    }
}
