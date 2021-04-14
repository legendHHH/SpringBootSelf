package com.legend.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * 索引查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/13
 */
public class EsTest_Client_Doc_Insert {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //创建索引
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user").id("1001");

        IndexResponse response = esClient.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println("索引操作：" + response.getAliases());
        System.out.println("索引操作2：" + response.getSettings());
        System.out.println("索引操作3：" + response.getMappings());

        //关闭客户端
        esClient.close();
    }
}
