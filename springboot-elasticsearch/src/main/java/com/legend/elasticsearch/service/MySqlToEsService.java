package com.legend.elasticsearch.service;

import com.alibaba.fastjson.JSONObject;
import com.legend.elasticsearch.entity.Item;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * mysql数据同步到es搜索引擎服务
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/8
 */
@Service
public class MySqlToEsService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private Logger logger = LoggerFactory.getLogger(MySqlToEsService.class);

    /**
     * 查询数据
     *
     * @throws Exception
     */
    public void selectPro() throws Exception {
        //获取client客户端
        Client client = elasticsearchTemplate.getClient();

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("mysql_es");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(new MultiMatchQueryBuilder("test", "user", "message"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest).actionGet();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            // do something with the SearchHit
            System.out.println(hit.getSourceAsString());
        }

    }

    /**
     * mysql查询数据同步到es
     *
     * @throws Exception
     */
    public void createAndUpdate() throws Exception {
        logger.info("elasticsearchTemplate客户端：{}", elasticsearchTemplate.getMapping(Item.class));

        String sql = "select * from test_to_es LIMIT 100";
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&autoReconnect=true";
        String user = "root";
        String pwd = "123456";
        Class.forName(driver);

        Connection conn = DriverManager.getConnection(url, user, pwd);
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        String id = "";
        String name = "";
        String age = "";
        String username = "";
        String password = "";
        String phone = "";
        String mailbox = "";

        //获取client客户端
        Client client = elasticsearchTemplate.getClient();

        Map<String, String> jsonMap = new HashMap<>(16);
        //批量处理类
        BulkRequest bulkrequest = new BulkRequest();
        //批量从mysql中引到ES
        while (rs.next()) {
            id = rs.getString("id");
            name = rs.getString("name");
            age = rs.getString("age");
            username = rs.getString("username");
            password = rs.getString("password");
            phone = rs.getString("phone");
            mailbox = rs.getString("mailbox");

            jsonMap.put("id", id);
            jsonMap.put("name", name);
            jsonMap.put("age", age);
            jsonMap.put("username", username);
            jsonMap.put("password", password);
            jsonMap.put("phone", phone);
            jsonMap.put("mailbox", mailbox);

            IndexRequest request = new IndexRequest("mysql_es", "test_to_es")
                    .id(id).source(jsonMap);
            bulkrequest.add(request);
        }
        BulkResponse bulkResponse = client.bulk(bulkrequest).actionGet();
        logger.info(JSONObject.toJSONString(bulkResponse));

    }


    /**
     * 查询数据
     *
     * @throws Exception
     */
    public void select() throws Exception {
        //获取client客户端
        Client client = elasticsearchTemplate.getClient();

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("mysql_es").types("test_to_es");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        String[] includeFields = new String[] {"user"};
//        String[] excludeFields = new String[] {""};
//        searchSourceBuilder.fetchSource(includeFields, excludeFields);
//        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("我是老八"));
        //searchSourceBuilder.query(new MultiMatchQueryBuilder("test", "user", "message"));
        searchSourceBuilder.query(new MultiMatchQueryBuilder("test+45", "phone"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest).actionGet();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            // do something with the SearchHit
            logger.info("查询的数据结果：" + hit.getSourceAsString());
        }

    }

    /**
     * 最简单的添加索引数据
     *
     * @throws Exception
     */
    public void insertOneDataToEs() throws Exception {
        //获取client客户端
        Client client = elasticsearchTemplate.getClient();

        Map<String, Object> jsonMap = new HashMap<>(16);
        jsonMap.put("name", "legend");
        jsonMap.put("age", "23");
        jsonMap.put("username", "test999");
        IndexRequest request = new IndexRequest("mysql_es", "test_to_es")
                .id("999").source(jsonMap);
        IndexResponse indexResponse = client.index(request).actionGet();
        logger.info("保存数据结果信息" + indexResponse.toString());
    }
}
