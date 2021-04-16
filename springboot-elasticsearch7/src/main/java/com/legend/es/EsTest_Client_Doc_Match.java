package com.legend.es;

import com.legend.es.utils.BeanMapUtil;
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
 * 文档条件查询
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/16
 */
public class EsTest_Client_Doc_Match {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //条件查询数据 termQuery
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //取出年龄为30的数据
        searchSourceBuilder.query(QueryBuilders.termQuery("name", "legend6"));
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        System.out.println("文档查询1：" + searchHits.getHits().length);
        System.out.println("文档总条数1：" + searchHits.getTotalHits());
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
