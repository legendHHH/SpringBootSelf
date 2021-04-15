package com.legend.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * 文档数据删除
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/15
 */
public class EsTest_Client_Doc_Delete {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //删除数据
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index("user").id("1011");

        DeleteResponse response = esClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(response.getId());
        System.out.println(response.getVersion());
        System.out.println(response.getResult());

        //关闭客户端
        esClient.close();
    }
}
