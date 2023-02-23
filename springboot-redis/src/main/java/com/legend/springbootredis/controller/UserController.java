package com.legend.springbootredis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import com.legend.springbootredis.entity.User;
import com.legend.springbootredis.service.RedisService;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author legend
 */
@Controller
public class UserController {

    private static final String mapName="mapName";
    @Autowired
    private RedisService redisService;

    @GetMapping( "/add.do")
    @ResponseBody
    public Map<Object, Object> addUser(HttpServletRequest request){
        Map<String, Object> modelMap=new HashMap<String,Object>();
        User user=new User();
        user.setName("hehename");
        user.setPassword("hehePassword");
        //存放hash值
        modelMap.put("name", user.getName());
        modelMap.put("password", user.getPassword());
        redisService.setKey(mapName, modelMap);
        //获取map集合
        Map<Object, Object> modelMap1= redisService.getMapValue(mapName);
        Object value= redisService.getValue(mapName, "name");
        System.out.println(" value : "+value);
        modelMap1.put("从缓存中根据key取到的value", value);
        return modelMap1;
    }


    @GetMapping( "/delete.do")
    @ResponseBody
    public Map<Object, Object> deleteUser(HttpServletRequest request){
        //获取即将删除的key值，这里我们做的批量删除
        List<String> keys=new ArrayList<>();
        keys.add("heheanme");
        //开始执行删除操作
        redisService.deleteData(keys);
        //获取map集合
        Map<Object, Object> modelMap1= redisService.getMapValue(mapName);
        Object value= redisService.getValue(mapName, "name");
        System.out.println(" value : "+value);
        modelMap1.put("从缓存中根据key取到的value", value);
        return modelMap1;
    }


    /**
     * 基于Redis GEO找到离你最近的店铺信息
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/redisgeo.do")
    @ResponseBody
    public String redisGeoTest() throws ExecutionException, InterruptedException {
        RedissonClient redissonClient = redisService.getRedissonClient();
        RGeo<String> geo = redissonClient.getGeo("message:shop");

        //初始化三家店铺信息
        GeoEntry shopA = new GeoEntry(111.56, 27.51, "shopA");
        GeoEntry shopB = new GeoEntry(112.54, 26.51, "shopB");
        GeoEntry shopC = new GeoEntry(113.56, 25.51, "shopC");

        //添加店铺
        geo.add(shopA, shopB, shopC);

        //获取店铺信息
        Map<String, GeoPosition> pos = geo.pos("shopA", "shopB", "shopC");
        System.out.println("A店的位置：" + pos.get("shopA"));
        System.out.println("B店的位置：" + pos.get("shopB"));
        System.out.println("C店的位置：" + pos.get("shopC"));


        //计算距离 假设 停车场有个经纬度，111.89  26.13 找到最近的店铺  有哪些店
        Map<String, Double> stringDoubleMap = geo.radiusWithDistanceAsync(111.53, 26.13, 200, GeoUnit.KILOMETERS, GeoOrder.ASC).get();
        for (String key : stringDoubleMap.keySet()) {
            System.out.println(key + "离你的距离为：" + stringDoubleMap.get(key));
        }
        return null;
    }

}