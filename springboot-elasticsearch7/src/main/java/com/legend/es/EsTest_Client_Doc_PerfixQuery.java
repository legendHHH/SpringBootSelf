package com.legend.es;

import com.legend.es.utils.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * 文档前缀查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2023/1/8
 */
public class EsTest_Client_Doc_PerfixQuery {

    public static void main(String[] args) throws Exception {
        //创建es客户端
        RestHighLevelClient esClient = EsClientUtil.getRestHighLevelClientWithUserNameAndPassword("localhost", 9200, "elastic", "123456");

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("knowledge");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //前缀查询 查询XoSI下面的子目录id包含XoSI （XoSI001、XoSI001001001等）
        PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery("catalogId", "string");

        searchSourceBuilder.query(prefixQueryBuilder);
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
