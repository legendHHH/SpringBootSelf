## SpringBoot整合Redis

### StringRedisTemplate在进行批量删除操作
#### 1.建立Spring boot项目，引入Redis依赖（pom.xml如下）：
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.test</groupId>
    <artifactId>redis</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>redis</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.6.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

#### 2.编写spring boot配置文件，这里我配置的内容简单，如需要其他的配置可以进官网去查
```
#Redis 
spring.redis.host=localhost
spring.redis.password=admin
spring.redis.port=6379

server.port=8081
```

#### 3.建立实体类：User
```
package com.legend.springbootredis.entity;

public class User {
    private Integer id;
    private String name;
    private String password;

    public User() {
        super();
    }

    public User(Integer id, String name, String password) {
        super();
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password + "]";
    }

}
```


#### service层，主要对redis的各种操作方法进行定义

```
package com.legend.springbootredis.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Resource
    private StringRedisTemplate template;

    /**
     * 存储数据或修改数据
     * 
     * @param modelMap
     * @param mapName
     */
    public void setKey(String mapName, Map<String, Object> modelMap) {
        HashOperations<String, Object, Object> hps = template.opsForHash();
        hps.putAll(mapName, modelMap);
    }

    /**
     * 获取数据Map
     * 
     * @param mapName
     * @return
     */
    public Map<Object, Object> getMapValue(String mapName) {
        HashOperations<String, Object, Object> hps = this.template.opsForHash();
        return hps.entries(mapName);

    }

    /**
     * 获取数据value
     * 
     * @param mapName
     * @param hashKey
     * @return
     */
    public Object getValue(String mapName, String hashKey) {
        HashOperations<String, Object, Object> hps = this.template.opsForHash();
        return hps.get(mapName, hashKey);

    }

    /**
     * 批量删除缓存数据
     * 
     * @param keys
     */
    public void deleteData(List<String> keys) {
        // 执行批量删除操作时先序列化template
        template.setKeySerializer(new JdkSerializationRedisSerializer());
        template.delete(keys);
    }
}
```



#### controller层代码，演示操作（添加与获取值）：
```
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
```

>我们上面用的是StringRedisTemplate，但是它存在一点问题。在我们往缓存中存入数字形式的String类型时，我们在利用Spring could将获取到的数据发送到另一服务时，我们发现数据已经被强转为Integer类型了，因为我们可能传输的数据庞大，类型多样，为了统一类型，以及开发方便，所以我将缓存改成RedisTemplate这种类型，进行增删改查的操作


### RedisTemplate的使用来存储Map集合
#### 修改application.properties
```
#redis
spring.redis.host=localhost
spring.redis.password=admin
spring.redis.port=6379
spring.redis.timeout=10000
spring.redis.jedis.pool.max-idle=200 
spring.redis.jedis.pool.min-idle=300000    
spring.redis.jedis.pool.max-active=400
spring.redis.jedis.pool.max-wait=10000
```


#### 我们写配置配置类实例化RedisTemplate
```
package com.legend.springbootredis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisConfig {

    /**
     * 实例化 RedisTemplate 对象
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> functionDomainRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 设置数据存入 redis 的序列化方式,并开启事务
     * 
     * @param redisTemplate
     * @param factory
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        // 如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to
        // String！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(factory);
    }

}
```


#### 写缓存操作的Service层，进行增删改查方法的定义：
```
package com.legend.springbootredis.service;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Service;

/**
 * 使用RedisTemplate 操作
 * @author legend
 */
@Service
public class RedisServiceNew {

    @Resource
    private RedisTemplate<String,Object> template;

    /**
     * 存储数据或修改数据
     *
     * @param modelMap
     * @param mapName
     */
    public void setKey(String mapName, Map<String, Object> modelMap) {
        HashOperations<String, String, Object> hps = template.opsForHash();
        hps.putAll(mapName, modelMap);
    }

    /**
     * 获取数据Map
     *
     * @param mapName
     * @return
     */
    public Map<String, Object> getMapValue(String mapName) {
        HashOperations<String, String, Object> hps = this.template.opsForHash();
        return hps.entries(mapName);

    }

    /**
     * 获取数据value
     *
     * @param mapName
     * @param hashKey
     * @return
     */
    public Object getValue(String mapName, String hashKey) {
        HashOperations<String, String, Object> hps = this.template.opsForHash();
        return hps.get(mapName, hashKey);

    }

    /**
     * 批量删除缓存数据
     *
     * @param keys
     */
    public void deleteData(List<String> keys) {
        // 执行批量删除操作时先序列化template
        template.setKeySerializer(new JdkSerializationRedisSerializer());
        template.delete(keys);
    }

}

```


#### 编写Controller层，来实现缓存的操作
```
package com.legend.springbootredis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.legend.springbootredis.entity.User;
import com.legend.springbootredis.service.RedisServiceNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 注入RedisServiceNew
 * @author legend
 */
@Controller
public class UserControllerNew {

    private static final String mapName="mapName";

    @Autowired
    private RedisServiceNew redisService;

    @GetMapping( "/templateAdd.do")
    @ResponseBody
    public Map<String, Object> addUser(HttpServletRequest request){
        Map<String, Object> modelMap=new HashMap<String,Object>();
        User user=new User();
        user.setName("legend");
        user.setPassword("newPassword");
        //存放hash值
        modelMap.put("name", user.getName());
        modelMap.put("password", user.getPassword());
        redisService.setKey(mapName, modelMap);
        //获取map集合
        Map<String, Object> modelMap1= redisService.getMapValue(mapName);
        Object value= redisService.getValue(mapName, "name");
        System.out.println(" value : "+value);
        modelMap1.put("从缓存中根据key取到的value", value);
        return modelMap1;
    }

    @GetMapping( "/templateDelete.do")
    @ResponseBody
    public Map<String, Object> deleteUser(HttpServletRequest request){
        //获取即将删除的key值，这里我们做的批量删除
        List<String> keys=new ArrayList<>();
        keys.add("heheanme");
        //开始执行删除操作
        redisService.deleteData(keys);
        //获取map集合
        Map<String, Object> modelMap1= redisService.getMapValue(mapName);
        Object value= redisService.getValue(mapName, "name");
        System.out.println(" value : "+value);
        modelMap1.put("从缓存中根据key取到的value", value);
        return modelMap1;
    }

}

```