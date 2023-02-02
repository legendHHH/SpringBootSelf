package com.legend.es;

import org.apache.http.HttpHost;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;

/**
 * 文档嵌套参数条件查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/12/16
 */
public class EsTest_Client_Doc_Nested {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("product2");

        BoolQueryBuilder shouldBuilder = new BoolQueryBuilder();
        shouldBuilder.should(nestedQuery("markingSkillList",
                QueryBuilders.matchQuery("markingSkillList.content", "兰博"), ScoreMode.None)
                .ignoreUnmapped(true));
        searchSourceBuilder.query(shouldBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        System.out.println("文档查询：" + searchHits.getHits().length);
        System.out.println("文档总条数：" + searchHits.getTotalHits());
        System.out.println(response.getTook());
        for (SearchHit hit : searchHits.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }

        //关闭客户端
        esClient.close();
    }
}
