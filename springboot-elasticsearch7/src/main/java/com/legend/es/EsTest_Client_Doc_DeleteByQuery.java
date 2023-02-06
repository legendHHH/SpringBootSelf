package com.legend.es;

import com.legend.es.utils.EsClientUtil;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;

/**
 * 文档数据条件删除
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/1/10
 */
public class EsTest_Client_Doc_DeleteByQuery {

    public static void main(String[] args) throws Exception {
        //创建es客户端
        RestHighLevelClient esClient = EsClientUtil.getRestHighLevelClientWithUserNameAndPassword("localhost", 9200, "elastic", "123456");

        //分词式删除数据
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest("user");
        deleteByQueryRequest.setQuery(new MatchQueryBuilder("name", "国年"));

        BulkByScrollResponse response = esClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
        System.out.println(response.getDeleted());
        System.out.println(response.getTotal());
        System.out.println(response.getBatches());

        //关闭客户端
        esClient.close();
    }
}
