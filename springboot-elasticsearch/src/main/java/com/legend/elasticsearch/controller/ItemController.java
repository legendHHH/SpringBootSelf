package com.legend.elasticsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.legend.elasticsearch.config.EsConfig;
import com.legend.elasticsearch.entity.EsParam;
import com.legend.elasticsearch.entity.Item;
import com.legend.elasticsearch.respository.ItemRepository;
import com.legend.elasticsearch.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 商品控制器
 * 具体可以参考 com.legend.elasticsearch.IndexTest测试类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/17
 */
@RestController
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    /**
     * 业务类
     */
    @Autowired
    private ItemRepository itemRepository;

    /**
     * 序列化和反序列化使用
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 远程调用使用
     */
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 创建索引库和类型测试
     *
     * @return
     */
    @GetMapping("/create/index")
    public String createIndex() {
        System.out.println("hello");
        Item item = new Item(1L, "小米手机7", " 手机",
                "小米", 3499.00, "http://image.leyou.com/13123.jpg");
        itemRepository.save(item);

        Item itemRepositoryById = itemRepository.findById(1L).get();

        StringBuffer result = null;
        try {
            result.append(objectMapper.writeValueAsString(itemRepositoryById));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    /**
     * 远程调用es的api(基于restTemplate不能用)
     *
     * @return
     */
    @Deprecated
    @GetMapping("/es/rest/api")
    public String restEsApi() {
        System.out.println("hello");

        //添加请求头参数
        Map<Object, Object> uriMapParam = new HashMap<Object, Object>();
        uriMapParam.put("Content-Type", "application/json;charset=utf-8");
        uriMapParam.put("kbn-name", "kibana");
        uriMapParam.put("kbn-xpack-sig", "kbn-xpack-sig");
        uriMapParam.put("kbn-xsrf", "kbn-version:6.6.0");
        uriMapParam.put("kbn-version", "6.6.0");
        log.info("当前Map集合长度为:{}", uriMapParam.size());

        EsParam esParam = new EsParam();
        esParam.setPath("/item");
        esParam.setMethod("HEAD");
        String result = restTemplate.postForObject("http://localhost:5601/api/console/proxy?path=/item&method=HEAD", uriMapParam, String.class);
        return result;
    }


    @Autowired
    private EsConfig esConfig;

    /**
     * 远程调用es的api(基于HttpClient不能用)
     * <p>
     * http://localhost:8012/es/http/api
     *
     * @return
     */
    @GetMapping("/es/http/api")
    @ResponseBody
    public String httpEsApi() {
        String result = esConfig.get();
        return result;
    }


    /**
     * Es搜索引擎根据索引和id查询信息
     * <p>
     * http://localhost:8012/queryEsInfo?indexName=item&typeName=docs&id=10
     * curl -X GET 127.0.0.1:9200/item/docs/10
     *
     * @return
     */
    @GetMapping(value = "/queryEsInfo")
    @ResponseBody
    public String queryEsByIdAndIndexAndName(@RequestParam("indexName") String indexName,
                                             @RequestParam("typeName") String typeName,
                                             @RequestParam(value = "esDealType", required = false) String esDealType,
                                             @RequestParam(value = "id", required = false) Integer id) {

        //限制不能频繁查询
        String queryEsTimestamp = (String) redisTemplate.opsForValue().get("query_es_timestamp");
        long currentTimestamp = System.currentTimeMillis();
        if (StringUtils.isEmpty(queryEsTimestamp)) {
            redisTemplate.opsForValue().set("query_es_timestamp", String.valueOf(currentTimestamp), 3, TimeUnit.MINUTES);
        } else {
            long esTimestamp = Long.parseLong(queryEsTimestamp);
            if (currentTimestamp - esTimestamp < 3000) {
                return "访问太频繁,稍后重试";
            }
        }
        //curl http://192.168.0.189:9200/product1/product_type/_mapping
        String[] cmds = {"curl", "http://localhost:9200/" + indexName + "/" + typeName + "/" + id};
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
        }
        return indexName + "成功" + typeName + "   id：" + id;
    }

}
