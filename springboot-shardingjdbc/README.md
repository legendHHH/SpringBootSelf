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

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210618132648161-822096285.png)


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


#### Sharding-JDBC公共表
1.存储固定数据的表,表数据很少发生变化,查询时候经常进行关联 
2.在每个数据库中都创建相同结构的表

```sql
CREATE TABLE `t_udict` (
  `dictid` int(11) NOT NULL AUTO_INCREMENT,
  `ustatus` varchar(20) NOT NULL,
  `uvalues` varchar(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dictid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```


### 遇到的问题

一个实体类对应两张表(spring.main.allow-bean-definition-overriding=true)，设置两张表的操作用坐一个实体类
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210616112212323-1547782074.png)


- 解决 There is no getter for property named ‘null‘ in ‘class 报错
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210618222325423-3877274.png)

在使用MyBatis-plus自带的删除和更新方法时，它都是通过ID来进行删除和更新，而我们的实体类没有id这个字段。

>解决方案：在实体类主键字段上加一个@TableId注解
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210618222625707-113260671.png)


### Sharding JDBC 读写分离
#### 概念
  为了确保数据库产品的稳定性,很多数据库拥有双机热备功能。也就是,第一台数据库服务器,是对外提供增删改业务的生产服务器;
第二台数据库服务器,主要进行读的操作。

原理：让主数据库（master）处理事务性增、改、删操作，而从数据库（slave）处理SELECT查询操作。

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210618223237511-492108873.png)


#### 读写原理
- 主从复制:当主服务器有写入(insert/update/delete)语句的时候,从服务器自动获取

- 读写分离:insert/update/delete语句操作一台服务器,select操作另一个服务器。

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210618223453470-1915725559.png)


>Sharding-JDBC读写分离则是根据SQL语义的分析,将读操作和写操作分别路由至主库与从库。它提供透明化读写分离,让使用方尽量像使用一个数据库一样使用主从数据库集群。


### MySQL配置读写分离配置 

约定：**主数据库5.6-->3306**    **从数据库5.7-->3308**

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210618230004615-816570191.png)


- 修改mysql配置

具体配置文件修改 查看 mysql-conf 里面的主从配置文件即可


>修改完配置记得重启主库和从库


- 创建用于主从复制的账号

```
# 切换至主库bin目录，登录主库
mysql ‐h localhost ‐uroot ‐p
# 授权主备复制专用账号
GRANT REPLICATION SLAVE ON *.* TO 'db_sync'@'%' IDENTIFIED BY 'db_sync';
# 刷新权限
FLUSH PRIVILEGES;
# 确认位点 记录下文件名以及位点
show master status;


-- 命令集合
CREATE USER 'db_sync'@'%' IDENTIFIED BY 'db_sync';
GRANT REPLICATION SLAVE ON *.* TO 'db_sync'@'%' IDENTIFIED BY 'db_sync';
FLUSH PRIVILEGES;
show master status;
```
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210618230229708-1662972991.png)

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210618230304468-731307773.png)

mysql-bin.000048   199



- 设置主库向从库同步数据(主从数据同步 设置)

```
# 切换至从库bin目录，登录从库
mysql ‐h localhost ‐P3308 ‐uroot ‐p

# 先停止同步
STOP SLAVE;

# 修改从库指向到主库，使用上一步记录的文件名以及位点
CHANGE MASTER TO
master_host = 'localhost',
master_user = 'db_sync',
master_password = 'db_sync',
master_log_file = 'mysql-bin.000048',
master_log_pos = 199;

# 启动同步
START SLAVE;

# 查看从库状态Slave_IO_Runing和Slave_SQL_Runing都为Yes说明同步成功，如果不为Yes，请检查error_log，然后排查相关异常
SHOW SLAVE STATUS;


# Slave_SQL_Runing  No  解决
STOP SLAVE;
set GLOBAL SQL_SLAVE_SKIP_COUNTER=1;
START SLAVE;


# 注意 如果之前此从库已有主库指向 需要先执行以下命令清空
STOP SLAVE IO_THREAD FOR CHANNEL '';
reset slave all;
```


原因分析：
  1.程序可能在slave上进行了写操作

  2.也可能是slave机器重起后,事务回滚造成的
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210618231842946-417190779.png)

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210618232007933-984678917.png)


### Sharding-Proxy

透明化的数据库代理端

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620135112717-1042409383.png)


>Sharding-Proxy独立应用,使用安装服务,进行分库分表或者读写分离配置,启动使用


#### Sharding-Proxy的安装

[下载 Sharding-Proxy-4.0.0版本](https://www.apache.org/dyn/closer.cgi?path=incubator/shardingsphere/4.0.0/apache-shardingsphere-incubating-4.0.0-sharding-proxy-bin.tar.gz)


- 修改conf/server.yaml

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620143501787-1394514289.png)


- 修改conf/config-sharding.yaml
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620144206904-512893705.png)


`分表配置`
```yaml
schemaName: sharding_db

dataSources:
 ds_0:
   url: jdbc:mysql://127.0.0.1:3306/db0?serverTimezone=UTC&useSSL=false
   username: root
   password: 123456
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
 ds_1:
   url: jdbc:mysql://127.0.0.1:3306/db1?serverTimezone=UTC&useSSL=false
   username: root
   password: 123456
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50

shardingRule:
 tables:
   t_order:
     actualDataNodes: ds_${0}.t_order_${0..1}
     tableStrategy:
       inline:
         shardingColumn: order_id
         algorithmExpression: t_order_${order_id % 2}
     keyGenerator:
       type: SNOWFLAKE
       column: order_id
   # t_order_item:
     # actualDataNodes: ds_${0..1}.t_order_item_${0..1}
     # tableStrategy:
       # inline:
         # shardingColumn: order_id
         # algorithmExpression: t_order_item_${order_id % 2}
     # keyGenerator:
       # type: SNOWFLAKE
       # column: order_item_id
 bindingTables:
   - t_order #,t_order_item
 defaultDatabaseStrategy:
   inline:
     shardingColumn: user_id
     algorithmExpression: ds_0
 defaultTableStrategy:
   none:

```

- 启动Sharding-Proxy 服务
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620144958014-1521036613.png)


按照报错信息找到类似名字的文件修改成.jar 包即可  具体修改了几个不记得了
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620145026661-757313922.png)

最后启动成功的标志(默认是3307  可以指定  start.bat 3308 即可)
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620150019027-1341768042.png)


- Sharding-Proxy启动端口进行连接

(1)cmd方式：mysql -P3307 -uroot -p
(2)Navicat方式：
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620150343713-1822124234.png)


连接问题
1.ERROR 10002 (C1000): 2Unknown exception: [Cannot support database type 'MySQL']
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620152134715-427900847.png)

解决：
下载shardingsphere-sql-parser-mysql-4.0.0 的jar包  原来的无法修改 提示不存在该项目
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620152318610-1048746899.png)

重启sharding-proxy 
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620152418015-1715091037.png)

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620152537464-1029083876.png)


Cause: java.lang.IllegalStateException: no table route info
解决：
由于进入分表策略的时候逻辑表匹配不到物理真实的表，看一下自己的分片策略，是否能够匹配到真实的表




建表和初始化数据
```sql
CREATE TABLE IF NOT EXISTS ds_0.t_order (order_id BIGINT NOT NULL,user_id INT NOT NULL,status VARCHAR(50),PRIMARY KEY(order_id));

INSERT INTO t_order (order_id, user_id, status) VALUES (11,1,'init');
```

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620153143739-213229426.png)


查看3307中的数据
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620153231297-1659615700.png)


再查看3306的表和数据(都是自动创建的)
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620153520024-705430108.png)




`分库配置`
```yaml
schemaName: sharding_db

dataSources:
 ds_0:
   url: jdbc:mysql://127.0.0.1:3306/db0?serverTimezone=UTC&useSSL=false
   username: root
   password: 123456
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
 ds_1:
   url: jdbc:mysql://127.0.0.1:3306/db1?serverTimezone=UTC&useSSL=false
   username: root
   password: 123456
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50

shardingRule:
 tables:
   t_order:
     actualDataNodes: ds_${0..1}.t_order_${0..1}
     tableStrategy:
       inline:
         shardingColumn: order_id
         algorithmExpression: t_order_${order_id % 2}
     keyGenerator:
       type: SNOWFLAKE
       column: order_id
   t_order_item:
     actualDataNodes: ds_${0..1}.t_order_item_${0..1}
     tableStrategy:
       inline:
         shardingColumn: order_id
         algorithmExpression: t_order_item_${order_id % 2}
     keyGenerator:
       type: SNOWFLAKE
       column: order_item_id
 bindingTables:
   - t_order ,t_order_item
 defaultDatabaseStrategy:
   inline:
     shardingColumn: user_id
     algorithmExpression: ds_${user_id % 2}
 defaultTableStrategy:
   none:
```
>修改配置之后需要重新初始化数据

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620160835044-283222861.png)


![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620160938823-1359102674.png)

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620161037334-1678556346.png)

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620160853304-584244449.png)



- 修改conf/config-master_slave.yaml

约定：db0是主库,db1、db2是从库


`读写分离配置`
```yaml
schemaName: master_slave_db

dataSources:
 master_ds:
   url: jdbc:mysql://127.0.0.1:3306/db0?serverTimezone=UTC&useSSL=false
   username: root
   password: 123456
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
 slave_ds_0:
   url: jdbc:mysql://127.0.0.1:3306/db1?serverTimezone=UTC&useSSL=false
   username: root
   password: 123456
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
 slave_ds_1:
   url: jdbc:mysql://127.0.0.1:3306/db2?serverTimezone=UTC&useSSL=false
   username: root
   password: 123456
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50

masterSlaveRule:
 name: ms_ds
 masterDataSourceName: master_ds
 slaveDataSourceNames:
   - slave_ds_0
   - slave_ds_1
```

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620162228406-1844518873.png)



在主数据库和从数据库创建相同表

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620162317419-1202092674.png)


```sql
CREATE TABLE IF NOT EXISTS db0.t_order_item (order_item_id BIGINT NOT NULL,order_id BIGINT NOT NULL,user_id INT NOT NULL,status VARCHAR(50),PRIMARY KEY(order_item_id));
CREATE TABLE IF NOT EXISTS db1.t_order_item (order_item_id BIGINT NOT NULL,order_id BIGINT NOT NULL,user_id INT NOT NULL,status VARCHAR(50),PRIMARY KEY(order_item_id));
CREATE TABLE IF NOT EXISTS db2.t_order_item (order_item_id BIGINT NOT NULL,order_id BIGINT NOT NULL,user_id INT NOT NULL,status VARCHAR(50),PRIMARY KEY(order_item_id));

-- 做了读写分离,则数据只会在db0(主库)插入
INSERT INTO t_order_item (order_item_id,order_id, user_id, status) VALUES (1,11,1,'init_order_item');
```

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620163135712-1273366446.png)


向表添加记录,不指定向哪个库添加; 
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620163938433-967600562.png)

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620163714966-1038959449.png)


查询数据查询不到,因为没做主从复制,所以没有从库中数据
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620164004518-1454257132.png)


手动往从库里面添加数据查询测试
```sql
INSERT INTO db1.t_order_item (order_item_id,order_id, user_id, status) VALUES (3,11,1,'init_order_item');
INSERT INTO db2.t_order_item (order_item_id,order_id, user_id, status) VALUES (4,11,1,'init_order_item');
```
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620164640943-1112391170.png)


数据库实现了随机查询
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210620164937137-2060446257.png)




### 总结





from：
https://www.bilibili.com/video/BV1LK411s7RX?p=1&spm_id_from=pageDriver