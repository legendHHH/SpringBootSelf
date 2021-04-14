### 正排(正向)索引

id              content
1001            my name is zhang san
1002            my name is li si

查询：Zhang san


### 倒排索引

keyword     id
name        1001,1002
zhang       1001



ElasticSearch里存储文档数据和关系型数据库MySQL存储数据的概念进行一个类比

ElasticSearch       Index(索引)           Type(类型)    Document(文档)    Fields(字段)

MySQL               Database(数据库)       Table(表)    Row(行)          Column(行)


这里Type的概念已经逐渐弱化,ElasticSearch6.X中,一个index下面已经只能包含一个type，ElasticSearch7.X中,Type的概念已经被删除



### 索引操作
创建索引
对比关系型数据库，创建索引就等同于创建数据库


PUT具有幂等性

POST不具有幂等性



### 文档操作
创建文档
Es服务器发post请求：http://127.0.0.1/shopping/_doc



match叫全文检索匹配
match_phrase :完全匹配



{
	"aggs":{//聚合操作
		"price_group":{ // 名称，随意起名
			"terms":{ //分组操作
				"field":"price" // 分组字段
			}
		}
	}
}



17集



tel 不能被索引 就是不能被作为字段查询