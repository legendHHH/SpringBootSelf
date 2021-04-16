package com.legend.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * 文档分页条件查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/16
 */
public class EsTest_Client_Doc_MatchPage {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //条件查询数据 termQuery
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        //取出年龄为30的数据
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.termQuery("age", "30"));
        //(当前页码 - 1) * 每页页数
        builder.from(2);
        builder.size(2);
        searchRequest.source(builder);

        SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        System.out.println("文档查询3：" + searchHits.getHits().length);
        System.out.println("文档总条数3：" + searchHits.getTotalHits());

        for (SearchHit hit : searchHits) {
            System.out.println(hit.getSourceAsMap());
        }

        //关闭客户端
        esClient.close();
    }
}
