package com.legend.es;

import com.legend.es.utils.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * 文档通配符模糊查询
 * ?，它与任何单个字符匹配
 * *，可以匹配零个或多个字符，包括一个空字符
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2023/1/9
 */
public class EsTest_Client_Doc_WildcardQuery {

    public static void main(String[] args) throws Exception {
        //创建es客户端
        RestHighLevelClient esClient = EsClientUtil.getRestHighLevelClientWithUserNameAndPassword("localhost", 9200, "elastic", "123456");

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("knowledge");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //模糊查询类似MySQL数据库的Like查询
        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("title.keyword", "惠普*");

        //UNLIKE
        //QueryBuilders.boolQuery().mustNot(QueryBuilders.wildcardQuery("title.keyword", "*惠普*"));

        searchSourceBuilder.query(wildcardQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponse：" + searchResponse.toString());
        SearchHits searchHits = searchResponse.getHits();
        for (SearchHit hit : searchHits.getHits()) {
            System.out.println("SourceAsMap：" + hit.getSourceAsMap());
        }
        System.out.println("Clusters：" + searchResponse.getClusters());
        System.out.println("TotalHits：" + searchResponse.getHits().getTotalHits());

        //关闭客户端
        esClient.close();
    }
}
