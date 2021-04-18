package com.legend.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * 文档聚合查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/18
 */
public class EsTest_Client_Doc_AggregationQuery {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //聚合查询
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //取名字叫 maxAge  对age的字段取最大值
        AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");
        searchSourceBuilder.aggregation(aggregationBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse.toString());
        SearchHits searchHits = searchResponse.getHits();
        for (SearchHit hit: searchHits.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
        System.out.println(searchResponse.getClusters());
        System.out.println(searchResponse.getHits().getTotalHits());


        //关闭客户端
        esClient.close();
    }
}
