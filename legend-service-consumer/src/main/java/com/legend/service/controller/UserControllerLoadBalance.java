package com.legend.service.controller;

import com.legend.service.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
@RequestMapping("/consumer2/user2")
//指定默认全局的熔断方法
@DefaultProperties(defaultFallback = "defaultFallback")
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
    @HystrixCommand(fallbackMethod = "queryUserByIdFallback") //指定两个方法关联起来,触发熔断方法  声明熔断的方法
    public String queryUserById(@RequestParam("id") Long id){
        return this.restTemplate.getForObject("http://service-provider/user/"+id, String.class);
    }

    //修改为字符串返回值
    /*public User queryUserById(@RequestParam("id") Long id){
        //换成动态的服务
//        List<ServiceInstance> instances = discoveryClient.getInstances("service-provider");
//        ServiceInstance instance = instances.get(0);
        //根据服务名动态获取服务的ip和端口地址
        return this.restTemplate.getForObject("http://service-provider/user/"+id, User.class);
        //return this.restTemplate.getForObject("http://localhost:8099/user/"+id, User.class);
    }*/

    /**
     * queryUserById的熔断方法局部的  需要和被熔断的方法的参数和返回值一致
     * @param id
     * @return
     */
    public String queryUserByIdFallback(@RequestParam("id") Long id){
        return "服务正忙,请稍后再试";
    }

    /**
     * 定义全局的熔断方法
     *  需要注意被熔断的方法参数在每一个方法中都不一样,所以作为全局的熔断方法不能加参数
     * @return
     */
    public String defaultFallback(){
        return "全局服务正忙,请稍后再试。。。。。。。。。。。。。。";
    }
}
