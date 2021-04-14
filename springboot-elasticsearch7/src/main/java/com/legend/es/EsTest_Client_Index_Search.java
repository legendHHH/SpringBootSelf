package com.legend.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

/**
 * 索引查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/13
 */
public class EsTest_Client_Index_Search {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //查询索引
        GetIndexRequest getIndexRequest = new GetIndexRequest("user");

        GetIndexResponse response = esClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);

        System.out.println("索引操作：" + response.getAliases());
        System.out.println("索引操作2：" + response.getSettings());
        System.out.println("索引操作3：" + response.getMappings());

        //关闭客户端
        esClient.close();
    }
}
