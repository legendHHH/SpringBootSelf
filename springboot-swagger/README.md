## SpringBoot整合Swagger 界面查看Restful API
swagger提供了html页面方便查看restful的api接口，以及测试功能，这样方便后期版本更新时的文档与代码的同步。
`项目整体目录结构`
![](https://img2018.cnblogs.com/blog/1231979/201911/1231979-20191109111554532-1153732367.png)


### 整合过程
#### 添加Swagger2依赖
```
<!--添加swagger2的依赖-->
<dependency>
     <groupId>io.springfox</groupId>
     <artifactId>springfox-swagger2</artifactId>
     <version>2.7.0</version>
 </dependency>
 <dependency>
     <groupId>io.springfox</groupId>
     <artifactId>springfox-swagger-ui</artifactId>
     <version>2.7.0</version>
 </dependency>
 <!--添加web模块-->
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-web</artifactId>
 </dependency>
```
![](https://img2018.cnblogs.com/blog/1231979/201911/1231979-20191109111524442-166237152.png)


#### 编写Swagger配置类
```
package com.legend.springbootswagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .tags(new Tag("HelloController",
                        "第一个Controller"),getTags())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("" +
                        "com.legend.springbootswagger"))
                .paths(PathSelectors.any()).build();
    }

    private Tag[] getTags() {
        Tag[] tags = {
                new Tag("HelloController","" +
                        "第一个Controller")
                ,new Tag("TestController","" +
                "第二个Controller")
        };
        return tags;
    }

    //设置API展示信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Swagger RESTful APIs")
                .description("Swagger API 服务")
                .termsOfServiceUrl("http://swagger.io/")
                .contact(new Contact("legend",
                        "xiaoqi.cr.cx", "737795279@qq.com"))
                .version("1.0")
                .license( "The Apache License, Version 2.0" )
                .licenseUrl( "http://www.apache.org/licenses" +
                        "/LICENSE-2.0.html" )
                .build();

    }
}

```

![](https://img2018.cnblogs.com/blog/1231979/201911/1231979-20191109112046924-1616485484.png)

>用@Configuration注解类，等于XML中配置beans；用@Bean标注方法等于XML中配置bean,@EnableSwagger2注解类表示启用swagger。

#### 编写测试接口
![](https://img2018.cnblogs.com/blog/1231979/201911/1231979-20191109112125349-171673286.png)

>tags的配置是根据Controller中的@Api的注解

#### 测试
![](https://img2018.cnblogs.com/blog/1231979/201911/1231979-20191109112308173-462029794.png)

![](https://img2018.cnblogs.com/blog/1231979/201911/1231979-20191109112345931-877193311.png)
