package com.legend.es;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.List;

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
        //searchRequest.indices("user");
        searchRequest.indices("search_hotwords");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //取名字叫 maxAge  对age的字段取最大值
        //AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");

        //热门词汇测试
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("hotWord").field("searchKeywords");
        searchSourceBuilder.aggregation(aggregationBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        //获取分析后的数据
        Aggregations aggregations = searchResponse.getAggregations();
        Terms term = aggregations.get("hotWord");
        List<? extends Terms.Bucket> buckets = term.getBuckets();
        List<String> hotWords = new ArrayList<>();
        for (Terms.Bucket bucket : buckets) {
            String key = (String) bucket.getKey();
            hotWords.add(key);
        }
        System.out.println(JSONObject.toJSONString(hotWords));

        //关闭客户端
        esClient.close();
    }
}
