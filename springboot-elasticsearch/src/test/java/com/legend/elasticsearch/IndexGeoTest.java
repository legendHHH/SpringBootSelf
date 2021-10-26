package com.legend.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.legend.elasticsearch.entity.ChainEsMappingDoc;
import com.legend.elasticsearch.respository.ChainRepository;
import com.legend.elasticsearch.vo.SearchChainVo;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

/**
 * 采用类的字节码信息创建索引并映射
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/10/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticsearchAppMain.class)
public class IndexGeoTest {

    private static final Logger log = LoggerFactory.getLogger(IndexGeoTest.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 创建索引库和类型(表结构映射)
     */
    @Test
    public void testCreate() {
        // 创建索引，会根据Item类的@Document注解信息来创建
        elasticsearchTemplate.createIndex(ChainEsMappingDoc.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        elasticsearchTemplate.putMapping(ChainEsMappingDoc.class);
    }

    @Autowired
    private ChainRepository chainRepository;

    /**
     * 新增文档(表数据)
     */
    @Test
    public void index() {
        ChainEsMappingDoc chain = new ChainEsMappingDoc();
        double lat = 22.544522;
        double lon = 114.11924;
        /*chain.setId(1L);
        chain.setStoreId(1L);
        chain.setChainCode("100386");
        chain.setChainName("深圳桃园");
        chain.setChainLat(22.531883);
        chain.setChainLon(113.921961);*/

        /*chain.setId(2L);
        chain.setStoreId(1L);
        chain.setChainCode("100330");
        chain.setChainName("深圳棕榈园");*/

        /*chain.setId(3L);
        chain.setStoreId(1L);
        chain.setChainCode("100387");
        chain.setChainName("国大药房君尚百货分店");*/

        chain.setId(4L);
        chain.setStoreId(1L);
        chain.setChainCode("100387");
        chain.setChainName("国大药房人民北分店");

        chain.setChainLat(lat);
        chain.setChainLon(lon);
        chain.setCreationDate(new Date());
        chain.setLastUpdateDate(new Date());
        chain.setDateStr("2021-10-21 11:39:30");
        chain.setLastUpdateDateStr("2021-10-21 11:59:30");
        chain.setCreationDateStr("2021-10-21 11:39:30");

        GeoPoint geoPoint = new GeoPoint(lat, lon);
        chain.setLocation(geoPoint);
        chainRepository.save(chain);
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        boolean deleteResult = elasticsearchTemplate.deleteIndex("deletetest");
        System.out.println("删除索引库是否成功..." + deleteResult);
    }

    /**
     * 批量新增
     */
    @Test
    public void indexList() {
        List<ChainEsMappingDoc> list = new ArrayList<>();
        list.add(new ChainEsMappingDoc());
        list.add(new ChainEsMappingDoc());
        // 接收对象集合，实现批量新增
        chainRepository.saveAll(list);
    }

    /**
     * 查询功能
     */
    @Test
    public void testQuery() {
        Optional<ChainEsMappingDoc> optional = this.chainRepository.findById(null);
        System.out.println(optional.get());
        this.chainRepository.deleteAll();
    }


    /**
     * 高级查询
     */
    @Test
    public void testSearch() {
        // 实现了SearchQuery接口，用于组装QueryBuilder和SortBuilder以及Pageable等
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        Pageable pageable =  PageRequest.of(0, 10);
        nativeSearchQueryBuilder.withPageable(pageable);

        //id=11的经纬度
        double latitude = 22.531883;
        double longitude = 113.921961;

        // 间接实现了QueryBuilder接口
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // 以某点为中心，搜索指定范围
        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("location");
        distanceQueryBuilder.point(latitude, longitude);
        // 定义查询单位：公里
        distanceQueryBuilder.distance(2500, DistanceUnit.KILOMETERS);
        boolQueryBuilder.filter(distanceQueryBuilder);
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);

        // 按距离升序
        GeoDistanceSortBuilder distanceSortBuilder =
                new GeoDistanceSortBuilder("location", latitude, longitude);
        distanceSortBuilder.unit(DistanceUnit.KILOMETERS);
        distanceSortBuilder.order(SortOrder.ASC);
        nativeSearchQueryBuilder.withSort(distanceSortBuilder);

        System.out.println("查询参数：" + JSONObject.toJSONString(nativeSearchQueryBuilder.build()));
        Page<ChainEsMappingDoc> search = this.chainRepository.search(nativeSearchQueryBuilder.build());
        //System.out.println(JSONObject.toJSONString(search));
        System.out.println("总条数：" + search.getTotalElements());
        System.out.println("搜索结果：" + search.getContent().toString());
        //search.forEach(item-> System.out.println(item));
    }

    /**
     * 使用elasticsearchTemplate返回Client进行自定义方法查询数据
     */
    @Test
    public void testGeoAndSort() {
        Integer pageNo = 1;
        Integer pageSize = 15;
        double latitude = 22.531883;
        double longitude = 113.921961;

        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("latitude", latitude);
        paramMap.put("longitude", longitude);
        paramMap.put("state", 0);

        // 使用自定义方法查询数据
        //获取client客户端
        Client client = elasticsearchTemplate.getClient();
        SearchRequestBuilder searchRequest = client.prepareSearch("geo").setTypes("mall_chain_type");
        //分页
        searchRequest.setFrom((pageNo - 1) * pageSize);
        searchRequest.setSize(pageSize);
        //间接实现了QueryBuilder接口
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //以某点为中心，搜索指定范围
        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("location");
        distanceQueryBuilder.point(latitude, longitude);
        // 定义查询单位：公里
        distanceQueryBuilder.distance(2500, DistanceUnit.KILOMETERS);
        boolQueryBuilder.filter(distanceQueryBuilder);

        //添加查询参数
        paramMap.forEach((k, v) -> {
            if (k.equals("state")) {
                log.info("进入state....");
                boolQueryBuilder.mustNot(QueryBuilders.termQuery(k, v));
            }
        });

        // 按距离升序
        GeoDistanceSortBuilder distanceSortBuilder =
                new GeoDistanceSortBuilder("location", latitude, longitude);
        distanceSortBuilder.unit(DistanceUnit.KILOMETERS);
        distanceSortBuilder.order(SortOrder.ASC);

        QueryBuilder queryBuilder = boolQueryBuilder;

        SearchResponse response = searchRequest.addSort(distanceSortBuilder).setQuery(queryBuilder).execute().actionGet();

        long totalCount = response.getHits().getTotalHits();
        log.info("内购商城-门店查询完整查询参数SearchRequest：{},es查询总条数totalCount:{}",searchRequest.toString(), totalCount);

        List<SearchChainVo> chainVoList = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> map = hit.getSourceAsMap();
            log.info("内购商城-查询的数据信息：{}", JSONObject.toJSONString(map));

            SearchChainVo vo = JSONObject.parseObject(JSONObject.toJSONString(map), SearchChainVo.class);

            //获取距离值，并保留两位小数点
            BigDecimal geoDis = new BigDecimal((double) hit.getSortValues()[0]);
            Map<String, Object> hitMap = hit.getSourceAsMap();
            //在创建MAPPING的时候，属性名的不可为geoDistance。
            hitMap.put("geoDistance", geoDis.setScale(2, BigDecimal.ROUND_HALF_DOWN));

            //返回好计算的距离
            vo.setGeoDistance(geoDis.setScale(2, BigDecimal.ROUND_HALF_DOWN));
            chainVoList.add(vo);
        }
        log.info("查询接口距离数据：{}", JSONObject.toJSONString(chainVoList));
    }

    /**
     * 使用自定义方法查询数据2
     */
    @Test
    public void testFindByPriceBetween() {
        // 使用自定义方法查询数据
        /*Iterable<Item> items = this.itemRepository.findByPriceBetween(3699D, 5999D);
        items.forEach(System.out::println);*/
        //items.forEach(item-> System.out.println(item));
    }

    /**
     * 自定义参数查询
     *//*
    @Test
    public void testNative() {
        // 构建自定义查询构建器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //添加基本的查询条件
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("title", "手机"));

        //执行查询获取数据结果集
        Page<Item> itemPage = this.itemRepository.search(nativeSearchQueryBuilder.build());
        log.info("总页数：{}", itemPage.getTotalPages());
        log.info("总条数：{}", itemPage.getTotalElements());

        //获取集合内容
        List<Item> itemList = itemPage.getContent();
        itemList.forEach(System.out::println);
    }

    /**
     * 测试分页
     */
    @Test
    public void testPage() {
        // 构建自定义查询构建器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //添加基本的查询条件
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("category", "手机"));
        //添加分页条件,页码是从0开始
        nativeSearchQueryBuilder.withPageable(PageRequest.of(1, 2));

        //执行查询获取数据结果集
        Page<ChainEsMappingDoc> itemPage = this.chainRepository.search(nativeSearchQueryBuilder.build());
        log.info("总页数：{}", itemPage.getTotalPages());
        log.info("总条数：{}", itemPage.getTotalElements());

        //获取集合内容
        List<ChainEsMappingDoc> itemList = itemPage.getContent();
        itemList.forEach(System.out::println);
    }


}