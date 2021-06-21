## Canal数据同步解决方案

### Canal是什么
  Canal是阿里巴巴旗下的一款开源项目,纯Java开发。基于数据库增量日志解析,提供增量数据订阅&消费,目前主要支持了MySQL(也支持mariaDB)。

  Canal是应阿里巴巴存在杭州和美国的双机房部署,存在跨机房同步的业务需求而提出的。
  不过早期的数据库同步业务，主要是基于trigger的方式获取增量变更，不过从2010年开始，阿里系公司开始逐步的尝试基于数据库的日志解析，获取增量变更进行同步，
由此衍生出了增量订阅&消费的业务，从此开启了一段新纪元。ps. 目前内部使用的同步，已经支持mysql5.x和Oracle部分版本的日志解析


### Canal同步原理
1.Canal模拟MySQL-slave的交互协议，伪装自己为MySQL-slave，向MySQL-master发送dump请求

2.MySQL master收到dump请求，开始推送binary-log给canal

3.Canal解析binary log对象(原始为byte流)

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210615210622348-416791052.png)


### Canal下载
[下载canal.deployer](https://github.com/alibaba/canal/releases/download/canal-1.0.17/canal.deployer-1.0.17.tar.gz)

OR
`wget https://github.com/alibaba/canal/releases/download/canal-1.0.17/canal.deployer-1.0.17.tar.gz`


### Canal 修改配置文件

- canal.properties
注意：如果没有整合消息队列则可以不需要修改(只需要注意canal.id= 1 不要重复了)


```properties
canal.zkServers =39.98.41.26:2181

# tcp, kafka, RocketMQ
canal.serverMode = kafka

canal.destinations = example

canal.mq.servers = 39.98.41.26:9092
canal.mq.retries = 0
canal.mq.batchSize = 16384
canal.mq.maxRequestSize = 1048576
canal.mq.lingerMs = 1
canal.mq.bufferMemory = 33554432
canal.mq.canalBatchSize = 50
canal.mq.canalGetTimeout = 100
canal.mq.flatMessage = true
canal.mq.compressionType = none
canal.mq.acks = all
```
>注:zookeeper端口2181;kafka端口9092


- conf/example/instance.properties
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210615211547723-1857846596.png)



### MySQL修改添加配置
- 在my.cnf 加入配置
```
[mysqld]  
log-bin=mysql-bin #添加这一行就ok  
binlog-format=ROW #选择row模式  
server_id=1233333 #配置mysql replaction需要定义，不能和canal的slaveId重复  
```

>查看如下图(相关命令：show variables like 'binlog_format%';  show variables like '%log_bin%';)

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210615212714970-992572038.png)

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210615212726336-640371502.png)



- 在MySQL中添加可以同步数据的用户
```
-- 创建用户
CREATE USER canal@'%' IDENTIFIED BY 'canal';

GRANT SELECT, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';

-- 刷新权限
FLUSH PRIVILEGES;
```


### Canal启动

```
./bin/startup.sh
./bin/stop.sh


-- canal server端运行日志
cat /canal/canal.log

-- canal client端连接日志
cat /canal/example/example.log
```

![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210615213627497-286024260.png)

>注意：后期会使用CentOS7重新操作一次



### Canal遇见问题
- [New I/O server worker #1-1] ERROR c.a.otter.canal.server.netty.handler.SessionHandler - something goes wrong with chann
![](https://img2020.cnblogs.com/blog/1231979/202106/1231979-20210615211748770-1514229698.png)


>解决方法有两种：(1)清空MySQL中binlog信息{position不一定为0}，然后将meta.dat中的binlog信息改为和MySQL一致{show master status; reset master;}   (2)替换：将meta.dat中的binlog信息改为和MySQL一致



es操作
https://github.com/alibaba/canal/wiki/Sync-ES
https://blog.csdn.net/qq_31442743/article/details/108272991
