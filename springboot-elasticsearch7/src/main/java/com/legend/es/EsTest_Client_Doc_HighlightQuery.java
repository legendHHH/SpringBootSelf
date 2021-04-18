package com.legend.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

/**
 * 文档高亮查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/18
 */
public class EsTest_Client_Doc_HighlightQuery {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //查询结果高亮
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        TermsQueryBuilder termQueryBuilder = QueryBuilders.termsQuery("name", "legend");

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.query(termQueryBuilder);
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
