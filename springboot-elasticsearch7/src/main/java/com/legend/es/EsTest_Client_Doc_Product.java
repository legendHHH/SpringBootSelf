package com.legend.es;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

/**
 * 产品库文档多条件查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/12/16
 */
public class EsTest_Client_Doc_Product {

    public static void main(String[] args) throws Exception {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                //es账号密码（默认用户名为elastic）
                new UsernamePasswordCredentials("elastic", "123456"));

        //创建带用户名密码的es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            @Override
                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                httpClientBuilder.disableAuthCaching();
                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            }
                        })
        );


        String keywords = "光大";

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("queue_product");

        //条件构造查询器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        //分页
        Pageable pageable = PageRequest.of(0, 1);
        nativeSearchQueryBuilder.withPageable(pageable);


        //根据关键词搜索产品库标题或内容
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("productName", keywords))
                .should(QueryBuilders.matchQuery("productDetail", keywords))
                .should(QueryBuilders.nestedQuery("answeringQuestionList", QueryBuilders.matchQuery("answeringQuestionList.answer", keywords), ScoreMode.None).ignoreUnmapped(true))
                .should(QueryBuilders.nestedQuery("markingSkillList", QueryBuilders.matchQuery("markingSkillList.content", keywords), ScoreMode.None).ignoreUnmapped(true)))
                .must(QueryBuilders.termQuery("deleteFlag", Boolean.FALSE))
                .must(QueryBuilders.termQuery("status", 1));
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);


        searchSourceBuilder.query(boolQueryBuilder);

        //高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //设置高亮字段
        highlightBuilder.field("productDetail");
        //如果要多个字段高亮,这项要为false
        highlightBuilder.requireFieldMatch(true);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsMap());
            System.out.println(hit.getHighlightFields());
        }

        long totalCount = response.getHits().getTotalHits().value;
        System.out.println(totalCount);

        //关闭客户端
        esClient.close();
    }
}
