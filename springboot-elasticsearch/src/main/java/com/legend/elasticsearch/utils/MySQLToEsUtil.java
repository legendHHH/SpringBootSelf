package com.legend.elasticsearch.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * mysql数据同步到es搜索引擎
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/8
 */
public class MySQLToEsUtil {

    public static void main(String[] args) throws Exception {
        selectPro();
        //createAndupdate();
        //select();
        //insertDataToEs();
    }

    /**
     * 查询数据
     *
     * @throws Exception
     */
    public static void selectPro() throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                ));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("mysql_es");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(new MultiMatchQueryBuilder("test", "user", "message"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
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
    public static void createAndupdate() throws Exception {
        String sql = "select * from test_to_es LIMIT 2000";
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
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                ));

        Map<String, String> jsonMap = new HashMap<>();
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
        BulkResponse bulkResponse = client.bulk(bulkrequest, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSONString(bulkResponse));

        client.close();
    }


    /**
     * 查询数据
     *
     * @throws Exception
     */
    public static void select() throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                ));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("posts");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        String[] includeFields = new String[] {"user"};
//        String[] excludeFields = new String[] {""};
//        searchSourceBuilder.fetchSource(includeFields, excludeFields);
//        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("我是老八"));
        //searchSourceBuilder.query(new MultiMatchQueryBuilder("test", "user", "message"));
        searchSourceBuilder.query(new MultiMatchQueryBuilder("test", "user", "message"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            // do something with the SearchHit
            System.out.println(hit.getSourceAsString());
        }

    }

    /**
     * 最简单的添加索引数据
     *
     * @throws Exception
     */
    public static void insertDataToEs() throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                ));

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "test");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "test2");
        IndexRequest request = new IndexRequest("posts", "user")
                .id("1").source(jsonMap);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
    }
}
