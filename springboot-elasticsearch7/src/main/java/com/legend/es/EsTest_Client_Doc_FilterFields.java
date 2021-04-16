package com.legend.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

/**
 * 文档查询字段过滤
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/16
 */
public class EsTest_Client_Doc_FilterFields {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //查询数据字段过滤
        SearchRequest searchRequest = new SearchRequest();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("age", 30));

        //字段过滤和不过滤数组
        //包含
        String[] includes = {"name"};
        //排除
        String[] excludes = {};
        searchSourceBuilder.fetchSource(includes, excludes);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse.getClusters());
        System.out.println(searchResponse.getHits());

        //关闭客户端
        esClient.close();
    }
}
