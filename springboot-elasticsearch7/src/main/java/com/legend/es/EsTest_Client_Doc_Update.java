package com.legend.es;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * 文档数据更新
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/15
 */
public class EsTest_Client_Doc_Update {

    public static void main(String[] args) throws Exception {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //修改数据
        UpdateRequest updateRequest = new UpdateRequest();
        String idStr = "1099";
        updateRequest.index("user").id(idStr);

        //局部修改数据
        updateRequest.doc(XContentType.JSON, "sex","女");

        UpdateResponse response = null;
        try {
            response = esClient.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println("ccc："+response.toString());
            System.out.println(response.getId());
            System.out.println(response.getVersion());
            System.out.println(response.getResult());
        } catch (Exception e) {
            System.out.println("出异常了："+e.getMessage());
            System.out.println("111111出异常了："+e.getMessage().contains("document missing"));

            if (e.getMessage().contains("document missing")) {
                IndexRequest indexRequest = new IndexRequest();
                indexRequest.index("user");
                indexRequest.id(idStr);
                User user = new User();
                user.setName("测试异常新增");
                user.setTel("120981234");
                indexRequest.source(JSONObject.toJSONString(user), XContentType.JSON);
                IndexResponse response2 = esClient.index(indexRequest, RequestOptions.DEFAULT);
                //IndexResponse[index=user,type=_doc,id=1099,version=1,result=created,seqNo=29,primaryTerm=6,shards={"total":2,"successful":1,"failed":0}]
                System.out.println(response2.toString());
                //201
                System.out.println(response2.status().getStatus());
            }

        }

        //关闭客户端
        esClient.close();
    }
}
