package com.legend.springbootredis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.legend.springbootredis.entity.User;
import com.legend.springbootredis.service.RedisService;
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

}