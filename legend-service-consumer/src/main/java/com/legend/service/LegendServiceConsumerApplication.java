package com.legend.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 主启动
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2019/10/28
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // 开启feign客户端
@EnableCircuitBreaker //开启熔断
//@SpringCloudApplication //组合注解,相当于 @SpringBootApplication  @EnableDiscoveryClient  @EnableCircuitBreaker
public class LegendServiceConsumerApplication {

    @Bean
    @LoadBalanced //开启负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(LegendServiceConsumerApplication.class, args);
    }

}
