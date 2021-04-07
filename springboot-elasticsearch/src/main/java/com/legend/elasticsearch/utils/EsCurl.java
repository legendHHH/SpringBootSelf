package com.legend.elasticsearch.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 通过curl来访问es搜索引擎内部数据
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/7
 */
@Component
public class EsCurl {
    private Logger LOGGER = LoggerFactory.getLogger(EsCurl.class);

    /**
     * 根据索引库和索引类型查询数据
     *
     * @param indexName 索引库名
     * @param typeName  索引类型名
     * @param id        业务主键id
     * @param esDealType  es的处理类型(_search/ _mapping等)
     * @return
     */
    public String queryEsByIndexAndName(String indexName, String typeName, Integer id, String esDealType) {
        //curl http://192.168.0.189:9200/product1/product_type/_mapping
        String ip = "192.168.0.119";
        if (!ip.startsWith("192.168")) {
            return "非法ip地址!!!";
        }
        String[] cmds = {"curl", "http://" + ip + ":9200/" + indexName + "/" + typeName + "/" + id};
        if (!StringUtils.isEmpty(esDealType) && esDealType != null) {
            switch (esDealType) {
                case "v":
                    //查看es全部索引的情况
                    cmds = new String[]{"curl", "http://" + ip + ":9200/_cat/indices?v"};
                    break;
                case "mapping":
                    //curl -X GET 'localhost:9200/sku1/sku_type/_mapping'
                    cmds = new String[]{"curl", "http://" + ip + ":9200/" + indexName + "/" + typeName + "/_mapping"};
                    break;
                case "search":
                    //curl -X GET 'localhost:9200/sku1/sku_type/_search'
                    cmds = new String[]{"curl", "http://" + ip + ":9200/" + indexName + "/" + typeName + "/_search"};
                    break;
                default:
                    break;
            }
        }
        ProcessBuilder builder = new ProcessBuilder(cmds);
        try {
            Process p = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("es配置信息在redis中维护不存在!!!!");
        }
        return null;
    }
}
