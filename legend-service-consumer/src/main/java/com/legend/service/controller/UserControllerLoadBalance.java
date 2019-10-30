package com.legend.service.controller;

import com.legend.service.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("consumer/user")
public class UserControllerLoadBalance {

    /**
     * 远程调用
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 包含了拉取的所有服务信息
     */
//    @Autowired
//    private DiscoveryClient discoveryClient;

    @GetMapping
    public User queryUserById(@RequestParam("id") Long id){
        //换成动态的服务
//        List<ServiceInstance> instances = discoveryClient.getInstances("service-provider");
//        ServiceInstance instance = instances.get(0);
        //根据服务名动态获取服务的ip和端口地址
        return this.restTemplate.getForObject("http://service-provider/user/"+id, User.class);
        //return this.restTemplate.getForObject("http://localhost:8099/user/"+id, User.class);
    }
}
