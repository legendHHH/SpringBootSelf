# SpringBoot与数据访问
## 1、JDBC

### 配置信息
- 依赖
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190721153321192-1032070155.png)

- yml
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190721153412180-481105517.png)

测试效果：
​	默认是用org.apache.tomcat.jdbc.pool.DataSource作为数据源；
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190721153653655-2012485491.png)


​	数据源的相关配置都在DataSourceProperties里面；

自动配置原理：

org.springframework.boot.autoconfigure.jdbc：
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190721154005385-163082307.png)

1、参考DataSourceConfiguration，根据配置创建数据源，默认使用Tomcat连接池；可以使用spring.datasource.type指定自定义的数据源类型；
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190721154228254-1332997284.png)

2、SpringBoot默认可以支持；

```
org.apache.tomcat.jdbc.pool.DataSource、HikariDataSource、BasicDataSource、
```

3、自定义数据源类型

```java
/**
 * Generic DataSource configuration.
 */
@ConditionalOnMissingBean(DataSource.class)
@ConditionalOnProperty(name = "spring.datasource.type")
static class Generic {

   @Bean
   public DataSource dataSource(DataSourceProperties properties) {
       //使用DataSourceBuilder创建数据源，利用反射创建响应type的数据源，并且绑定相关属性
      return properties.initializeDataSourceBuilder().build();
   }

}
```

4、**DataSourceInitializer：ApplicationListener**；

​	作用：

​		1）、runSchemaScripts();运行建表语句；

​		2）、runDataScripts();运行插入数据的sql语句；

默认只需要将文件命名为：

```properties
schema-*.sql、data-*.sql
默认规则：schema.sql，schema-all.sql；
可以使用   
	schema:
      - classpath:department.sql
      指定位置
```
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190721155526474-1637844694.png)

>注意：指定路径的时候不能有空格，不然会报错
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190721155643458-296038086.png)


5、操作数据库：自动配置了JdbcTemplate操作数据库

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190721215456134-1085484105.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190721215423804-677996767.png)

