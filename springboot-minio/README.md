# Getting Started--MinIO

## 搭建MinIO文件存储 (Windows)

### windows运行minio

- 首先下载安装包，windows下载点
[MinIO百度网盘下载](https://pan.baidu.com/s/1VsD9vFu9fXZz2Hd2wi9lNg)

提取码：h84h

- 安装启动
`启动一个cmd窗口，进入minio.exe所在文件夹，输入如下命令:`

```
.\minio.exe server D:\software\Minio
```

`启动结果显示:`

![](https://img2018.cnblogs.com/blog/1231979/202002/1231979-20200201120200857-2113192225.png)

>这里minio会给出ACCESS-KEY 和 SECRET-KEY，供后台管理登录使用。


![](https://img2018.cnblogs.com/blog/1231979/202002/1231979-20200201114057879-852562940.png)

>minio服务启动成功，其中D:\software\Minio 可设置成读者本地磁盘任何位置  后面是你图片上传之后的存储目录。



- 浏览器访问
![](https://img2018.cnblogs.com/blog/1231979/202002/1231979-20200201120624669-1820416291.png)

>输入ACCESS-KEY 和 SECRET-KEY即可登录minio的管理界面了。


- 创建桶和上传图片
`在后台管理界面可以创建你的Bucket(桶)，可以理解为一个文件夹用来存放图片。桶创建成功之后就可以上传图片了.`

![](https://img2018.cnblogs.com/blog/1231979/202002/1231979-20200201121347378-2053569827.png)



- 浏览器地址栏访问图片
`在文件列表的右边可以复制出图片的访问地址，在浏览器中就可以访问图片了。这个时候的图片地址是带过期时间和密钥的.`

![](https://img2018.cnblogs.com/blog/1231979/202002/1231979-20200201121703007-1091734242.png)

![](https://img2018.cnblogs.com/blog/1231979/202002/1231979-20200201121937628-49645954.png)


---
## 前后端分离项目，实现文件存储

### 上传流程示意图
![](https://img2018.cnblogs.com/blog/1231979/202002/1231979-20200201102250035-31340819.png)



### 结合SpringBoot 使用
`接下来我们将结合SpringBoot来实现一个完整的图片上传于删除操作.`

- 在pom.xml  中添加MinIO 的相关依赖
```
<!--MinIO JAVA SDK-->
<dependency>
    <groupId>io.minio</groupId>
    <artifactId>minio</artifactId>
    <version>3.0.10</version>
</dependency>
```

- 在SpringBoot中开启文件上传功能，需要在application.yml 添加如下配置：

```
spring:
  servlet:
    multipart:
      enabled: true #开启文件上传
      max-file-size: 10MB #限制文件上传大小为10M
```


- 添加一个MinioController控制器用于实现文件的上传和删除操作：

```
package com.legend.minio.controller;

import com.legend.minio.controller.dto.MinioUploadDto;
import com.legend.minio.result.CommonResult;
import io.minio.MinioClient;
import io.minio.policy.PolicyType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Minio文件存储对象控制器
 *
 * @author legend
 */
@Api(tags = "MinioController", description = "MinIO对象存储管理")
@Controller
@RequestMapping("/minio")
public class MinioController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MinioController.class);

    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;


    @ApiOperation("文件删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam("objectName") String objectName) {
        try {
            MinioClient minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
            minioClient.removeObject(BUCKET_NAME, objectName);
            return CommonResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.failed();
    }


    @ApiOperation("文件上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult upload(@RequestParam("file") MultipartFile file) {
        try {
            //创建一个MinIO的Java客户端
            MinioClient minioClient = new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
            //判断桶是否存在
            boolean isExist = minioClient.bucketExists(BUCKET_NAME);
            if (isExist) {
                LOGGER.info("存储桶已经存在！..........");
                LOGGER.info("error.bucket_exists");
            } else {
                //创建存储桶并设置只读权限
                minioClient.makeBucket(BUCKET_NAME);
                minioClient.setBucketPolicy(BUCKET_NAME, "*.*", PolicyType.READ_ONLY);
            }

            String filename = file.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            //设置存储对象名称
            String objectName = sdf.format(new Date()) + "/" + filename;
            // 使用putObject上传一个文件到存储桶中
            minioClient.putObject(BUCKET_NAME, objectName, file.getInputStream(), file.getContentType());
            LOGGER.info("文件上传成功!");

            //创建文件上传返回对象
            MinioUploadDto minioUploadDto = new MinioUploadDto();
            minioUploadDto.setName(filename);
            minioUploadDto.setUrl(ENDPOINT + "/" + BUCKET_NAME + "/" + objectName);

            return CommonResult.success(minioUploadDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.failed();

    }
}

```

- 在application.yml中对MinIO客户端进行配置：
```
# MinIO对象存储相关配置
minio:
  endpoint: http://127.0.0.1:9000 #MinIO服务所在地址
  bucketName: test-minio #存储桶名称
  accessKey: EIKDDECHC3X95UKHCUW7 #访问的key
  secretKey: DR+w+Paz5UpaQMwecrFyxJCQRXNGbWCATfxTxTfg #访问的秘钥
```


- 启动我的SpringBoot应用，使用Postman来访问上传接口进行文件上传，上传接口地址：

```
http://localhost:8080/minio/upload
```
![](https://img2018.cnblogs.com/blog/1231979/202002/1231979-20200201115840719-672455179.png)


- 上传完成后，我们打开MinIO的管理界面可以看到上传后的图片，也可以通过返回的url来访问图片：


![](https://img2018.cnblogs.com/blog/1231979/202002/1231979-20200201115626656-170450563.png)


![](https://img2018.cnblogs.com/blog/1231979/202002/1231979-20200201115707303-296796324.png)


- 调用删除接口来删除该图片


`需要注意的是objectName值是存储桶中的图片相对路径，`删除文件接口地址：http://localhost:8080/minio/delete

![](https://img2018.cnblogs.com/blog/1231979/202002/1231979-20200201122710874-1465040709.png)


- 结合Vue使用

我们的SpringBoot应用需要支持跨域请求，否则Vue前端无法进行接口调用，我们先添加一个全局的跨域请求配置：

```
package com.legend.minio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 全局跨域配置
 *
 * @author legend
 */
@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        //允许所有域名进行跨域调用
        config.addAllowedOrigin("*");
        //允许跨越发送cookie
        config.setAllowCredentials(true);
        //放行全部原始头信息
        config.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

```



