package com.qcl.mybatisplus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger2配置类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/20
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("" +
                        "com.qcl.mybatisplus"))
                .paths(PathSelectors.any()).build();
    }


    /**
     * 设置API展示信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Swagger RESTful APIs")
                .description("Swagger API 服务")
                .termsOfServiceUrl("http://swagger.io/")
                .contact(new Contact("legend",
                        "xiaoqi.cr.cx", "XXX@qq.com"))
                .version("1.0")
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses" +
                        "/LICENSE-2.0.html")
                .build();

    }
}
