## Neo4j学习

### 是什么？
- Neo4j是一个高性能、面向对象的图形数据库，它是为存储和查询大规模图形数据而设计的。
- Neo4j 是目前应用最为广泛的图数据库之一，它以图的形式存储和查询半结构化的数据，其亮点在于它支持一系列基于图数据结构的操作，例如长距离关系的发现、节点间路径的查询等等


### 类型版本
- Neo4j有3 种类型，分别是 Community Server、Enterprise Server 和 Desktop


>Neo4j 的底层依赖于 Java，因此在安装 Neo4j 之前需要先安装 Java
>不同版本的 Neo4j 依赖的 Java 版本也不同，具体来说，Neo4j 3.X 依赖 JDK 8，Neo4j 4.0+ 依赖 JDK 11


### Neo4j 安装
- 下载
[Neo4j Community Edition]( http://neo4j.org/download)

 
- 解压之后目录
![](https://img2023.cnblogs.com/blog/1231979/202306/1231979-20230627112927753-1245383375.png)

- 配置环境变量
NEO4J_HOME = neo4j安装目录

Path添加 = %NEO4J_HOME%\bin

- 修改配置 \conf目录下

```
# 限制 LOAD CSV 在导入文件时，只能从 `import` 目录下读取
# 将其注释之后，可以从文件系统的任意路径读取文件
dbms.directories.import=import


# 在默认设置下，只能从本地访问 neo4j
# 取消注释之后，可以从远程访问 neo4j
dbms.connectors.default_listen_address=0.0.0.0

# 是否允许 bolt 链接方式，默认是 true
dbms.connector.bolt.enabled=true

# bolt 的默认端口是 7687，也可以通过这行设置进行修改
dbms.connector.bolt.listen_address=:7687

# 是否允许 http 链接方式，默认是 true
dbms.connector.http.enabled=true

# http 的默认端口是 7474，也可以通过这行设置进行修改
dbms.connector.http.listen_address=:7474

# 是否允许 https 链接方式，默认是 true
dbms.connector.https.enabled=true

# https 的默认端口是 7473，也可以通过这行设置进行修改
dbms.connector.https.listen_address=:7473

# 是否允许 LOAD CSV 在导入文件时，可以从远程读取文件
dbms.security.allow_csv_import_from_file_urls=true

# neo4j 数据库是否只读
dbms.read_only=false
```


- 启动 neo4j.bat console


- 登陆 http://localhost:7474，提示输入密码，默认密码 `neo4j` ：
![](https://img2023.cnblogs.com/blog/1231979/202306/1231979-20230627112755501-951601857.png)



### Neo4j 增删改查

```
//node
CREATE (student1:Person {sid:'1001', name:'Steven', gender:'M', age:'18'})
CREATE (student2:Person {sid:'1002', name:'Mary', gender:'M', age:'19'})
//relationship
CREATE (student1)-[:classmate]->(student2)
```
[Neo4j官网详细查询文档](https://neo4j.com/docs/developer-manual/current/cypher/)



### Neo4j结合Java使用
[代码调用Neo4j(JAVA)下载驱动](https://neo4j.com/developer/language-guides/)

- 创建节点
```
private static void Create() {
     Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "XXX" ) );
     Session session = driver.session();

     session.run( "CREATE (a:Book {name:'helloworld'})" );

     session.close();
     driver.close();
 }
```

- 查询节点
```
private static void Query() {
     Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "123456" ) );//调用地址，用户名和密码
     Session session = driver.session();

     StatementResult result = session.run( "MATCH (a: Person) WHERE a.name = 'Andres' RETURN a.name AS name" );
     while ( result.hasNext() )
     {
         Record record = result.next();
         System.out.println( record.get("name").asString() );
     }

     session.close();
     driver.close();
 }
```

