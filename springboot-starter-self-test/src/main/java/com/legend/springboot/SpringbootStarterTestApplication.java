package com.legend.springboot;

import com.legend.springboot.starter.service.TestService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootStarterTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootStarterTestApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(TestService testService){
        //项目启动后，调用 TestService 的实例方法，输出方法的值
        return (args -> {
            System.out.println(testService.getServiceName());
        });
    }
}
