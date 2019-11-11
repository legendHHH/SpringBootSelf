package com.legend.springbootredis.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Resource
    private StringRedisTemplate template;

    /**
     * 存储数据或修改数据
     * 
     * @param modelMap
     * @param mapName
     */
    public void setKey(String mapName, Map<String, Object> modelMap) {
        HashOperations<String, Object, Object> hps = template.opsForHash();
        hps.putAll(mapName, modelMap);
    }

    /**
     * 获取数据Map
     * 
     * @param mapName
     * @return
     */
    public Map<Object, Object> getMapValue(String mapName) {
        HashOperations<String, Object, Object> hps = this.template.opsForHash();
        return hps.entries(mapName);

    }

    /**
     * 获取数据value
     * 
     * @param mapName
     * @param hashKey
     * @return
     */
    public Object getValue(String mapName, String hashKey) {
        HashOperations<String, Object, Object> hps = this.template.opsForHash();
        return hps.get(mapName, hashKey);

    }

    /**
     * 批量删除缓存数据
     * 
     * @param keys
     */
    public void deleteData(List<String> keys) {
        // 执行批量删除操作时先序列化template
        template.setKeySerializer(new JdkSerializationRedisSerializer());
        template.delete(keys);
    }
}