package com.legend.elasticsearch.respository;

import com.legend.elasticsearch.entity.ChainEsMappingDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * ElasticsearchRepository需要两个参数
 * 第一个是实体类,第二个是实体类的主键id的类型
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/17
 */
public interface ChainRepository extends ElasticsearchRepository<ChainEsMappingDoc, Long> {
}
