package com.legend.es;

import com.legend.es.utils.BeanMapUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.util.Map;

/**
 * 文档数据批量更新
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/21
 */
public class EsTest_Client_Doc_BatchUpdate {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //修改数据
        BulkRequest bulkRequest = new BulkRequest();

        //批量局部修改数据
        User user = new User();
        user.setTel("15607835338");
        user.setName("testName");
        Map<String, Object> objectMap = BeanMapUtil.objectToMap(user);
        objectMap.remove("id");
        bulkRequest.add(new UpdateRequest().index("user").id("1011").doc(new IndexRequest().source(objectMap)));
        bulkRequest.add(new UpdateRequest().index("user").id("1012").doc(new IndexRequest().source(BeanMapUtil.objectToMap(new User("op0yut", 99999)))));

        BulkResponse response = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response.getIngestTookInMillis());
        System.out.println(response.getTook());
        System.out.println(response.getIngestTook());

        //关闭客户端
        esClient.close();
    }
}
