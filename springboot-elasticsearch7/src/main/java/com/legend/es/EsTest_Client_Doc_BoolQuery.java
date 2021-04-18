package com.legend.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * 文档组合查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/18
 */
public class EsTest_Client_Doc_BoolQuery {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();


        //查询名字叫legend 并且年龄是30并且性别不是男的数据
        //BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //boolQueryBuilder.must(QueryBuilders.matchQuery("name","legend"));
        //boolQueryBuilder.must(QueryBuilders.matchQuery("age","30"));
        //boolQueryBuilder.mustNot(QueryBuilders.matchQuery("sex","男"));

        //查询名字叫legend 或者 legend3的数据
        //boolQueryBuilder.should(QueryBuilders.matchQuery("name","legend"));
        //boolQueryBuilder.should(QueryBuilders.matchQuery("name","legend3"));

        //searchSourceBuilder.query(boolQueryBuilder);

        //范围查询(年龄在 20-40之间的数据)
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
        rangeQueryBuilder.gt(10);
        rangeQueryBuilder.lte(40);
        searchSourceBuilder.query(rangeQueryBuilder);
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
