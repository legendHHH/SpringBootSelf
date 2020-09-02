package com.legend.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * eureka server服务端 (@EnableEurekaServer //启动Eureka服务端)
 *
 * @author legend
 */
@SpringBootApplication
@EnableEurekaServer
public class LegendEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LegendEurekaApplication.class, args);
    }

}
