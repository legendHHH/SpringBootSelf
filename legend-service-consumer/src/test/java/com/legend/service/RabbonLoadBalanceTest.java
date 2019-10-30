package com.legend.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbonLoadBalanceTest {

    @Autowired
    private LoadBalancerClient client;

    @Test
    public void test() {
        for (int i = 0; i < 50; i++) {
            ServiceInstance serviceInstance = this.client.choose("service-provider");
            System.out.println(serviceInstance.getHost()+"");
        }

    }
}
