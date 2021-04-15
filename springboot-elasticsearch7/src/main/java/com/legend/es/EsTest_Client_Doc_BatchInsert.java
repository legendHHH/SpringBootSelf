package com.legend.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * 文档数据批量新增
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/15
 */
public class EsTest_Client_Doc_BatchInsert {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //批量插入数据
        BulkRequest bulkRequest = new BulkRequest();

        bulkRequest.add(new IndexRequest().index("user").id("1021").source(XContentType.JSON, "name", "legend5"));
        bulkRequest.add(new IndexRequest().index("user").id("1032").source(XContentType.JSON, "name", "legend6"));
        bulkRequest.add(new IndexRequest().index("user").id("1043").source(XContentType.JSON, "name", "legend7"));

        BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println("花费时间："+bulkResponse.getTook());
        System.out.println("hello："+bulkResponse.getIngestTook());
        System.out.println("多个响应："+bulkResponse.getItems().toString());

        //关闭客户端
        esClient.close();
    }
}
