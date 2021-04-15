package com.legend.es;

import com.legend.es.utils.BeanMapUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * 文档查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/15
 */
public class EsTest_Client_Doc_Query {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //查询数据
        GetRequest getRequest = new GetRequest();
        getRequest.index("user").id("1011");

        GetResponse response = esClient.get(getRequest, RequestOptions.DEFAULT);

        System.out.println("文档查询：" + response.getSourceAsMap());
        //转化成Bean对象
        User user = BeanMapUtil.map2Object(response.getSourceAsMap(), User.class);
        if (user != null) {
            System.out.println(user.toString());
        }

        //关闭客户端
        esClient.close();
    }
}
