# SpringBoot与数据访问
## 3、整合MyBatis

```xml
<dependency>
	<groupId>org.mybatis.spring.boot</groupId>
	<artifactId>mybatis-spring-boot-starter</artifactId>
	<version>1.3.1</version>
</dependency>
```


### 3.1 注解版
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722220705631-623233813.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722221220483-1260202194.png)


`如果数据库的字段是有下划线的那种就会读取会空值`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722220738974-880913123.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722220746915-133696756.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722220757048-1785537507.png)


#### 解决办法
`自定义MyBatis的配置规则；给容器中添加一个ConfigurationCustomizer;`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722220939580-75056526.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722221205663-839981138.png)


----

`如果注释了mapper注解之后`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722221903499-1568352206.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722221755169-1227769866.png)


#### 解决办法
`使用MapperScan批量扫描所有的Mapper接口`

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722222025007-174591256.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722222104916-162204754.png)


### 3.2 配置文件版
```yaml
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml 指定全局配置文件的位置
  mapper-locations: classpath:mybatis/mapper/*.xml  指定sql映射文件的位置
```

更多使用参照
[官方文档]
(http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)


![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722223828352-151799189.png)

`测试`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722223531020-751105856.png)

>会发现dId是null值

#### 解决办法
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722224208097-1501837265.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190722224026340-1969343978.png)
