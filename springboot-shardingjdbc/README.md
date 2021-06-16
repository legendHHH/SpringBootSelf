## ShardingSphere

### ShardingSphere是什么
1.Apache ShardingSphere 是一套开源的分布式数据库解决方案组成的生态圈,
2.有三个产品：Sharding-JDBC、Sharding-Proxy 和 Sharding-Sidecar,
3.定位为关系型数据库中间件，合理在分布式环境下使用关系型数据库操作


Sharding-JDBC：分库分表操作
Sharding-Proxy：分库分表操作


[ShardingSphere官网](https://shardingsphere.apache.org/)

[ShardingSphere官网文档](https://shardingsphere.apache.org/document/current/cn/overview/)



### 什么是分库分表
  数据库数据量是不可控的，随着时间和业务发展，造成表里面数据越来越多，如果再去对数据库表crud的时候，会造成性能问题


方案1：从硬件上  
方案2：分库分表

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616145322755-1960273275.png)

>为了解决由于数据量过大而造成数据库性能降低问题



### 分库分表的方式
- 1.分库分表的两种方式：垂直切分和水平切分
- 2.垂直切分：垂直分表和垂直分库
- 3.水平切分：水平分表和水平分库


![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616150446111-1228793204.png)


#### 垂直分表
操作数据库中某张表,把这张表中一部分字段数据存储到另外一张新表里面,再把这张表另一部分字段数据存到另外一张表里面

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616150655965-1935426981.png)

>原来在一张表的时候,如果我们需要修改课程的其他信息,这个时候课程表会被锁起来,影响查询效率,无法访问。但是分表之后,还是可以查看课程的其他信息


`即时表拆分了但是还是在一个数据库,这时候数据库的IO还是很大,可以通过垂直分库来解决`


#### 垂直分库
把单一数据库按照业务进行划分,专库专表

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616151248875-1301949249.png)



#### 水平分库

- 垂直分库
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616151622295-720708384.png)


- 水平分库
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616151959695-811445522.png)

>添加数据规则 判断课程id是偶数  课程数据库A, 课程id是奇数  课程数据库B



#### 水平分表

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616152411259-337950296.png)


>总结：垂直：库和表是不一样的,水平：库和表是一样的
>垂直分表：是对表的字段做拆分，垂直分库：按照业务分，专库专用
>水平分库：


### 分库分表的应用和问题

#### 应用
- （1）在数据库设计的时候考虑垂直分库和垂直分表

- （2）随着数据库数据量增加,不要马上考虑做水平切分,首先考虑`缓存处理`,
`读写分离`,使用`索引`等等方式,如果这些方式不能根本解决问题,在考虑做水平分库分表处理。



#### 问题

- （1）跨节点连接查询问题(分页、排序)
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616153218039-1847699452.png)

- （2）多数据源管理问题



### Sharding-JDBC

#### 简介

- 它是轻量级 Java 框架,是增强版的 JDBC 驱动
- 主要目的是：简化分库分表之后数据相关操作


**注意：Sharding-JDBC不是做分库分表。它主要做两个功能：数据分片和读写分离**


![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616153501398-1362897685.png)



#### Spring-Boot+Mybatis-Plus+Druid连接池+Sharding-JDBC的示例-水平分表操作


- pom文件依赖
```
spring-boot-starter

spring-boot-starter-test

druid-spring-boot-starter

mysql-connector-java

sharding-jdbc-spring-boot-starter(4.0.0版本)

mybatis-plus-boot-starter
```


- 按照水平分表的方式来创建数据库，创建数据库表
数据库 db0
表：springboot-shardingjdbc/sql/course.sql

约定规则：如果添加课程id是偶数把数据添加到course_1、奇数则添加到course_2


- 配置Sharding-JDBC分片策略
在项目 application.properties配置文件中进行配置
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616155815688-766191035.png)

```yml
# 配置数据源别名
spring.shardingsphere.datasource.names=ds0

# 完善数据库配置连接信息
spring.shardingsphere.datasource.ds0.type=com.zaxxer.hikari.HikariDataSource
spring.shardingsphere.datasource.ds0.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.ds0.jdbc-url=jdbc:mysql://localhost:3306/db0?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.shardingsphere.datasource.ds0.username=root
spring.shardingsphere.datasource.ds0.password=123456

#指定表的位置,表策略,哪个数据库,表名称都是什么 行表达式  ds0.course_1、ds0.course_2
spring.shardingsphere.sharding.tables.course.actual-data-nodes=ds0.course_$->{1..2}

#指定分片策略：课程cid是偶数把数据添加到course_1、奇数则添加到course_2
spring.shardingsphere.sharding.tables.course.table-strategy.standard.sharding-column=cid
spring.shardingsphere.sharding.tables.course.table-strategy.inline.algorithm-expression=course_$->{cid % 2 + 1}

# 指定course表里面的主键生成策略   SNOWFLAKE
spring.shardingsphere.sharding.tables.course.key-generator.column=cid
spring.shardingsphere.sharding.tables.course.key-generator.type=SNOWFLAKE

# 打印sql输出日志
spring.shardingsphere.props.sql.show=true
```


- 测试类 com.qcl.shardingjdbc.ShardingjdbcMainTest

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616154913117-835865567.png)




#### Sharding-JDBC的示例-水平分库

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616163432533-1097046181.png)

```
spring.shardingsphere.sharding.tables.course.database-strategy.inline.sharding-column=user_id
spring.shardingsphere.sharding.tables.course.database-strategy.inline.algorithm-expression=ds${user_id % 2 + 1}

```


#### Sharding-JDBC的示例-垂直分库


### 遇到的问题

一个实体类对应两张表(spring.main.allow-bean-definition-overriding=true)，设置两张表的操作用坐一个实体类
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616112212323-1547782074.png)



Sharding JDBC 水平分库
