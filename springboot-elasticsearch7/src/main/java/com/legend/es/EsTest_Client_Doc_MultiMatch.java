package com.legend.es;

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
 * 文档多个字段匹配搜索关键词查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2023/1/6
 */
public class EsTest_Client_Doc_MultiMatch {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = EsClientUtil.getRestHighLevelClientWithUserNameAndPassword("localhost", 9200, "elastic", "123456");

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("string", "productName", "productCode"));
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        System.out.println("文档查询2：" + searchHits.getHits().length);
        System.out.println("文档总条数3：" + searchHits.getTotalHits());

        for (SearchHit hit : searchHits.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }

        //关闭客户端
        esClient.close();
    }
}
