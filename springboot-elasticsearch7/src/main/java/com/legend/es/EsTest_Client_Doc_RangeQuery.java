package com.legend.es;

import com.legend.es.utils.EsClientUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * 文档时间范围查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2023/1/9
 */
public class EsTest_Client_Doc_RangeQuery {

    public static void main(String[] args) throws Exception {
        //创建es客户端
        RestHighLevelClient esClient = EsClientUtil.getRestHighLevelClientWithUserNameAndPassword("localhost", 9200, "elastic", "123456");

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("queue_pro");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //范围查询
        //RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("lastUpdateTime").gte("2022-01-09 00:00:00").lte("2023-01-09 23:59:59").format("yyyy-MM-dd'T'HH:mm:ss+0800");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("lastUpdateTime").gte("2022-01-09 00:00:00").lte("2023-01-09 23:59:59").format("yyyy-MM-dd HH:mm:ss").timeZone("+08:00");

        searchSourceBuilder.query(rangeQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponseXXX：" + searchResponse.toString());
        SearchHits searchHits = searchResponse.getHits();
        for (SearchHit hit : searchHits.getHits()) {
            System.out.println("SourceAsMapXXX：" + hit.getSourceAsMap());
        }
        System.out.println("TotalHits：" + searchResponse.getHits().getTotalHits());

        //关闭客户端
        esClient.close();
    }
}
