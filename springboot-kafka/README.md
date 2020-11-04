1.启动zookeeper
zkServer
![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201031233515500-1571527278.png)


验证是否安装成功,双击zkCli.cmd
![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201031234111477-196927198.png)



2.启动kafka
bin\windows\kafka-server-start.bat config\server.properties

![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201031233355991-1446259764.png)
![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201031233734688-408757272.png)



3.创建topic
bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201031233824660-898526479.png)



4.查看topic
bin\windows\kafka-topics.bat --list --zookeeper localhost:2181

![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201031233901922-618881617.png)



5.启动producer
bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test

6.启动customer
bin\windows\kafka-console-consumer.bat --zookeeper localhost:2181 --topic test --from-beginning


看到接收到hello说明消费者接收消息成功了    到此就结束了
![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201031234709018-1407211813.png)
![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201031234717899-729788661.png)


9.整合到项目中

![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201031234940038-1117273735.png)

![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201031235021165-326409094.png)


![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201031235108926-318119068.png)
![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201031235122696-51515054.png)





如果项目不需要数据库相关信息就排除此类的autoconfig在@SpringBootApplication注解上加上exclude，解除自动加载DataSourceAutoConfiguration。

配置文件添加数据库链接信息
**. properties 文件添加数据库配置信息（以mysql为例）：

```
druid.driver=com.mysql.jdbc.Driver
druid.url=jdbc:mysql://localhost:3306/data?serverTimezone=Asia/Shanghai&allowMultiQueries=true
druid.username= root
druid.password= root
```


**.yml文件添加数据库配置信息（已mysql为例）：
```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/data?serverTimezone=Asia/Shanghai&allowMultiQueries=true
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
```

启动报错参考https://blog.csdn.net/zhenghhgz/article/details/88530524