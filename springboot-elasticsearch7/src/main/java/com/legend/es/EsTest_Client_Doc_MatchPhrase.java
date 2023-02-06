package com.legend.es;

import com.legend.es.utils.BeanMapUtil;
import com.legend.es.utils.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * 文档词组匹配查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/4/16
 */
public class EsTest_Client_Doc_MatchPhrase {

    public static void main(String[] args) throws Exception {
        //创建es客户端
        RestHighLevelClient esClient = EsClientUtil.getRestHighLevelClientWithUserNameAndPassword("localhost", 9200, "elastic", "123456");


        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //搜索华为 ---> 华    为 二字可以拆分查询，而matchPhraseQuery 是不可以拆分的
        //QueryBuilders.matchQuery("name", "华为")
        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("name", "华为"));
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        System.out.println("文档查询7：" + searchHits.getHits().length);
        System.out.println("文档总条数7：" + searchHits.getTotalHits());
        System.out.println(response.getTook());

        for (SearchHit hit : searchHits.getHits()) {
            System.out.println(hit.getSourceAsMap());
            User user = BeanMapUtil.map2Object(hit.getSourceAsMap(), User.class);
            System.out.println(user.toString());
        }

        //关闭客户端
        esClient.close();
    }
}
