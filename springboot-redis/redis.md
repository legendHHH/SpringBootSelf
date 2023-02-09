# Redis的关键字

高并发,KV存储,内存数据库,丰富的数据结构,单线程（6版本之前）


## 为什么出现缓存
一般情况下,数据都是在数据库中,应用系统直接操作数据库。当访问量上万,数据库压力增大,这个时候


>分库分表,读写分离。的确,这些确实是解决比较高的访问量的解决办法,但是,如果访问量更大,10万,100万呢？怎么分似乎都不解决问题吧,所以我们需要用到其他办法,来解决高并发带来的数据库压力。


缓存就是缓存数据到内存中一份,当访问的时候我们会访问内存的数据如果内存中的数据不存在,这个时候,我们再去
读取数据库,之后把数据库中的数据再备份一份到内存中,这样下次读请求过来的时候,还是会直接先从内存中访问,
访问到内存的数据了之后就直接返回了。


这样做就完美的降低了数据库的压力,可能十万个请求进来,全部都访问到了内存的备份数据,而没有去数据库访问数据,
或者只有少量几个请求访问到了数据库这样真的是大大降低了数据库的压力,而且这样做提高了系统响应。




## 什么是缓存？
缓存原指CPU上的一种高速存储器,它先于内存与CPU交换数据,速度很快。现在泛指存储在计算机上的原始数据的复制集,便于快速访问。


## Redis高效的核心要素
- 高速的存储介质  (存储介质随机访问延迟：机械硬盘--》10ms  固态硬盘--》100us  内存条--》150ns   单位换算：1000)
- 优良的底层数据结构  底层是HashMap 时间复杂度是O(1)
- 高效的网络IO模型  epoll  kqueue
- 高效的线程模型  IO多线程，执行单线程

## Redis分析
**redis底层是hashtable**


- 数组的下标索引计算：
hash(key) %hashtable.size=[0,hashtable.size-1]
 

- 产生hash冲突(采用链表法  next指针)
hash(key) %hashtable.size= 7
hash(key1) %hashtable.size= 7

指针next指向可以是：头插法和尾插法

>redis使用的是头插法,需要数据在下一次会被访问到。hash冲突越来越多的时候会进行成倍的扩容


## 缓存的读写模式

缓存有三种读写模式


### Cache Aside Pattern (常用)

Cache Aside Pattern (旁路缓存),是最经典的缓存+数据库读写模式

`读的时候,先读缓存,缓存没有的话,就读数据库,然后取出数据后放入缓存,同时返回响应。`

![](https://img2020.cnblogs.com/blog/1231979/202011/1231979-20201115221432339-1110952946.png)


更新的时候,先更新数据库,然后再删除缓存
![](https://img2020.cnblogs.com/blog/1231979/202011/1231979-20201115222447070-640301574.png)


#### 为什么是删除缓存,而不是更新缓存呢？

1.缓存的值是一个结构,hash,list,更新数据需要遍历

2.懒加载,使用的时候才更新缓存,也可以采用异步的方式填充缓存


#### 高并发脏读的三种情况
1.先更新数据库,在更新缓存  
    update与commit之间,更新缓存,commit失败,则DB与缓存数据不一致


2.先删除缓存,再更新数据库
    update与commit之间,有新的读,缓存空,读DB数据到缓存,数据是旧的数据
commit后DB为新的数据,则DB与缓存数据不一致


3.先更新数据库，再删除缓存（推荐）
    update与commit之间，有新的读，缓存空，读DB数据到缓存，数据是旧的数据
commit后DB为新的数据,则DB与缓存数据不一致`采用延时双删策略`。


## 延迟双删
#### 什么是延迟双删
延迟双删策略是分布式系统中数据库存储和缓存数据保持一致性常用策略，但它不是强一致性。其实不管采用那种方案，都避免不了Redis存在脏数据问题，
只能减轻这个问题，要想彻底解决，得要用到同步锁和对应的业务逻辑层面解决

#### 为什么要进行延迟双删
一般我们在更新数据库数据时，需要同步redis中缓存的数据，所以存在两种方式：

- 先执行update操作，再执行缓存清除。
- 先执行缓存清除，再执行update操作。

这两种方案的弊端是当存在并发请求时，很容易出现以下问题：

第一种方案：当请求1执行update操作后，还未来得及进行缓存清除，此时请求2查询到并使用了redis中的旧数据。
第二种方案：当请求1执行清除缓存后，还未进行update操作，此时请求2进行查询到了旧数据并写入了redis。


#### 如何实现延迟双删？
所以此时我们需要使用第三种方案：先进行缓存清除，再执行update，最后（延迟N秒）再执行缓存清除。


#### 需要注意的点
上述中（延迟N秒）的时间要大于一次写操作的时间，一般为3-5秒。
原因：如果延迟时间小于写入redis的时间，会导致请求1清除了缓存，但是请求2缓存还未写入的尴尬。。。

>ps:一般写入的时间会远小于5秒


#### 总结
- 延迟双删用比较简洁的方式实现 mysql 和 redis 数据最终一致性，但它不是强一致。
- 延迟，是因为 mysql 和 redis 主从节点数据同步不是实时的，所以需要等待一段时间，去增强它们的数据一致性。
- 延迟是指当前请求逻辑处理延时，而不是当前线程或进程睡眠延迟。
- mysql 和 redis 数据一致性是一个复杂的课题，通常是多种策略同时使用，例如：延迟双删、redis 过期淘汰、通过路由策略串行处理同类型数据、分布式锁等等。



### Read/Write Through Pattern
应用程序只操作缓存，缓存操作数据库
Read-Through（穿透读模式/直读模式）：应用程序读缓存，缓存没有，由缓存回源到数据库，并写入缓存
Write-Through（穿透写模式/直写模式）：应用程序写缓存，缓存写数据库。该种模式需要提供数据库的handler，开发较为复杂




### Write Behind Caching Pattern
应用程序只更新缓存

缓存通过异步的方式将数据批量或合并后更新到DB中

不能时时同步，甚至会丢数据


## 缓存分类
### 客户端缓存
- 页面缓存

- 浏览器缓存
浏览器缓存可分为强制缓存和协商缓存。

- app缓存


### 网络端缓存
- Web代理缓存

- 边缘缓存
边缘缓存中典型的商业化服务就是CDN了。
CDN的全称是Content Delivery Network，即内容分发网络。


### 服务端缓存
- 数据库级缓存
InnoDB存储引擎中的buffer-pool用于缓存InnoDB索引及数据块。

- 平台级缓存
平台级缓存指的是带有缓存特性的应用框架。
比如：GuavaCache 、EhCache（二级缓存，硬盘）、OSCache（页面缓存）等

- 应用级缓存(重点)
具有缓存功能的中间件：Redis、Memcached


## 缓存架构设计思路



## Redis是什么
Redis是一个高性能的开源的，C语言的NoSQL(非关系型数据库)也叫做缓存数据库,数据保存在内存中,Redis是以key-value形式存储，和传统的关系型数据库不一样。不一定遵循传统数据库的那些基本要求。比如，不遵循SQL标准，事务，表结构等。
Redis有非常丰富的数据类型，比如String，list，set，zset，hash等



## Redis可以做什么
1.减轻数据库压力，提高并发量，提高系统响应时间

2.做Session分离
    传统的session是由自己的tomcat进行维护和管理的，再集群和分布式情况下，不同的tomcat要管理不同的session，只能在各个tomcat之间，通过网络和IO进行session复制极大影响了系统的性能
Redis解决了这一个问题，将登陆成功后的session信息，存放在Redis中，这样多个tomcat就可以共享Session信息了    

![](https://img2020.cnblogs.com/blog/1231979/202011/1231979-20201115225103914-216092337.png)

    
    
3.做分布式锁
一般Java中的锁都是多线程锁，是在一个进程中的，多个进程在并发的时候也会产生问题，也要控制时序性，这个时候Redis可以用来做分布式锁，使用Redis的setnx命令来实现


### Redis缺点
1.额外的硬件支出
缓存是一种软件系统中以空间换时间的技术，需要额外的磁盘空间和内存空间来存储数据


2.高并发缓存失效
在高并发的情况下，会出现缓存失效（缓存穿透，缓存雪崩，缓存击穿等问题）造成瞬间数据库访问量增大，甚至崩溃，所以这些问题是一定要去解决的


3.缓存与数据库数据同步
缓存与数据库无法做到数据的时时同步


4.缓存并发竞争
多个Redis客户端同时对一个key进行set值的时候由于执行顺序引起的并发的问题



### Redis的Key的设计
- 1. 用:分割
- 2. 把表名转换为key前缀, 比如: user:
- 3. 第二段放置主键值
- 4. 第三段放置列名


## Redis的数据结构
Redis是一个key-value的存储系统，key的类型是字符串

Redis没有表的概念，Redis实例所对应的db以编号区分，db本身就是key的命名空间。
    比如：user:1000作为key值，表示在user这个命名空间下id为1000的元素，类似于user表的id=1000的行。


Redis中常见的value的数据类型，有五种，string，list，hash，set，zset

![](https://img2023.cnblogs.com/blog/1231979/202302/1231979-20230209153418023-1841260741.png)



key类型设计：
string->
   1.char[] data="legend\0"
   2.SDS：simple dynamic string
      key：data="sdfgdss\0fgdhshsjs"

redis的源码分析
   在server.h  --> redisDb.h 头文件里面
   dict字典


>相同的类型(type)  底层的编码(encoding)也会不相同
>同一个类型在内存中用不同的数据结构存储这种就是编码


>当redis 服务器初始化时，会预先分配 16 个数据库。id是数据库序号，为0-15（默认Redis有16个数据库）


RedisDB结构体源码
![](https://img2023.cnblogs.com/blog/1231979/202302/1231979-20230209153640566-1504736624.png)



### String字符串类型
String适合做单值缓存，对象缓存，分布式锁等


#### SET key value [EX seconds] [PX milliseconds] [NX|XX]

`时间复杂度：O(1)`
`将字符串值value关联到key如果key已经持有有其他值，set就会覆写旧值，无视类型。当 set 命令对一个带有生存时间（ttl）的键进行设置之后， 该键原有的 ttl 将被清除。`
`返回值：总是OK` 

参数解析：
- EX seconds：将key的过期时间设置为seconds秒。执行 `SET key value EX seconds` 的效果等同于执行 `SETEX key seconds value`
- PX milliseconds：将key的过期时间设置为 milliseconds 毫秒。执行 `SET key value PX milliseconds` 的效果等同于执行 `PSETEX key milliseconds value` 。
- NX：只有key不存在时，才能对key进行设置操作。执行 `SET key value NX` 的效果等同于执行 `SETNX key value` 。
- XX：只有key已经存在时，才能对key进行操作


命令示例：
```
SET ===> set key value 赋值
         set k "v" EX 100 赋值并设置过期时间
         set k2 "v" NX 赋值（如果key已经存在，设置失败）
         set k3 "v" XX 赋值（如果key原来不存在，设置失败）
```


#### SETNX key value

`时间复杂度：O(1)`
`只在key不存在的情况下,才能将key的值设置为value。`
`返回值：成功1，失败0`

命令示例：
```
SETNX ===> setnx key value  SETNX是『SET if Not eXists』的简写
```


#### SETEX key seconds value

`时间复杂度：O(1)`
`将key的值设置为value,并将key的生存时间设置为seconds秒。如果key已经存在,那么SETEX命令将覆盖已有的值。`
`返回值：OK`


命令示例：
```
SETEX ===> setex key 60 val SETEX是『SET eXpire』的简写
```


#### PSETEX key milliseconds value

`时间复杂度：O(1)`
`将key的值设置为value,并将key的生存时间设置为milliseconds毫秒。`
`返回值：OK`


命令示例：
```
PSETEX ===> PSETEX mykey 10000 "Hello" 
```


#### GET key

`时间复杂度：O(1)`
`返回与键 key 相关联的字符串值。如果key的值并非字符串类型，那么返回一个错误，因为GET命令只能用于字符串值。`
`返回值：key存在返回value，key不存在返回nil`


命令示例：
```
GET ===> get key 取值
```

#### GETSET key value

`时间复杂度：O(1)`
`将key的值设为value,并返回key在被设置之前的旧值。`
`返回值：key对应的旧值，key设置前不存在返回nil`


命令示例：
```
GETSET ===> getset key value 获取的是这个key上一次的值显示并赋值最新的值（如果是新的key则会显示nil）
```


#### STRLEN key

`时间复杂度：O(1)`
`将key的值设为value,并返回key在被设置之前的旧值。`
`返回值：返回key储存的字符串值的长度。`


命令示例：
```
STRLEN ===> STRLEN key 获取的是这个key长度
```

![](https://img2020.cnblogs.com/blog/1231979/202011/1231979-20201117091554948-1592297842.png)



#### incr key
递增数字

#### incrby key increment
增加指定的整数

#### decrdecr key
递减数字

#### decrby key decrement
减少指定的整数

#### mset key value key value
批量赋值

#### mget key key
批量取值


### list列表类型
list列表类型可以存储有序、可重复的元素
获取头部或尾部附近的记录是极快的
list的元素个数最多为2^32-1个（40亿）

![](https://img2023.cnblogs.com/blog/1231979/202302/1231979-20230209141845140-273305222.png)


#### lpush key v1 v2 v3 ...
从左侧插入列表


#### lpop key
从列表左侧取出

#### rpush key v1 v2 v3 ...
从右侧插入列表

#### rpop key 
从列表右侧取出

#### lpushx key value 
将值插入到列表头部

#### rpushx key value 
将值插入到列表尾部


#### blpop key timeout
从列表左侧取出，当列表为空时阻塞，可以设置最大阻塞时
间，单位为秒


#### brpop key timeout
从列表右侧取出，当列表为空时阻塞，可以设置最大阻塞时
间，单位为秒


#### llen key 
获得列表中元素个数


#### lindex key index 
获得列表中下标为index的元素 index从0开始


#### lrange key start end 
返回列表中指定区间的元素，区间通过start和end指定


#### lrem key count value
删除列表中与value相等的元素
当count>0时， lrem会从列表左边开始删除;当count<0时，
lrem会从列表后边开始删除;当count=0时， lrem删除所有
值为value的元素


#### lset key index value 
将列表index位置的元素设置成value的值


#### ltrim key start end 
对列表进行修剪，只保留start到end区间


#### rpoplpush key1 key2 
从key1列表右侧弹出并插入到key2列表左侧


#### brpoplpush key1 key2 
从key1列表右侧弹出并插入到key2列表左侧，会阻塞


#### linsert key BEFORE/AFTER pivot value
将value插入到列表，且位于值pivot之前或之后



##### 应用场景：
1、作为栈或队列使用
列表有序可以作为栈和队列使用

Stack(栈)=LPUSH+LPOP

Queue(队列)=LPUSH+RPOP

BlockingMQ(阻塞队列)=LPUSH+BRPOP



2、可用于各种列表
比如用户列表、商品列表、评论列表等。


3.微博和微信公众号消息流
![](https://img2023.cnblogs.com/blog/1231979/202302/1231979-20230209150133871-1148685115.png)

>微博和公众号都是新发的消息是在最上面的


本地宝发微博，消息ID为100000018：LPUSH msg:{ID} 100000018

信访局发微博，消息ID为100000099：LPUSH msg:{ID} 100000099

查看最新微博消息：LRANGE msg:{ID} 0 4

>如果微博大V，或者微信大V，关注比较高的，几千个，上万个，可以分批发，比如先给在线的发，这样就很快了




### set集合类型
无序、唯一元素

![](https://img2023.cnblogs.com/blog/1231979/202302/1231979-20230209143611471-1277849976.png)


#### sadd key mem1 mem2 .... 
为集合添加新成员


#### srem key mem1 mem2 .... 
删除集合中指定成员


#### smembers key
获得集合中所有元素


#### spop key 
返回集合中一个随机元素，并将该元素删除


#### srandmember key 
返回集合中一个随机元素，不会删除该元素


#### scard key 
获得集合中元素的数量


#### sismember key member 
判断元素是否在集合内


#### sinter key1 key2 key3 
求多集合的交集


#### sdiff key1 key2 key3 
求多集合的差集


#### sunion key1 key2 key3 
求多集合的并集


##### 应用场景：
适用于不能重复的且不需要顺序的数据结构
比如：关注的用户，还可以通过spop进行随机抽奖


**微信抽奖小程序**
- 1.点击参与抽奖加入集合 SADD key {userId}

- 2.查看参与抽奖所有用户：SMEMBERS key

- 3.抽取count名中奖者：SRANDMEMBER key [count] / SPOP key [count]

![](https://img2023.cnblogs.com/blog/1231979/202302/1231979-20230209150718221-2115110354.png)



**微信微博点赞，收藏，标签**
- 点赞：SADD like:{消息ID} {用户ID}

- 取消点赞：SREM like:{消息ID} {用户ID}

- 检查用户是否点过赞：SISMEMBMR like:{消息ID} {用户ID}

- 获取点赞的用户列表：SMEMBERS like:{消息ID}

- 获取点赞用户数：SCARD like:{消息ID}




### ZSet(sortedset)有序集合类型
SortedSet(ZSet) 有序集合： 元素本身是无序不重复的
每个元素关联一个分数(score)
可按分数排序，分数可重复

![](https://img2023.cnblogs.com/blog/1231979/202302/1231979-20230209144732854-1227522932.png)


#### zadd key score1 member1 score2 member2 ... 
为有序集合添加新成


#### zrem key mem1 mem2 .... 
删除有序集合中指定成员


#### zcard key 
获得有序集合中的元素数量


#### zcount key min max 
返回集合中score值在[min,max]区间的元素数量


#### zincrby key increment member 
在集合的member分值上加increment


#### zscore key member 
获得集合中member的分值


#### zrank key member 
获得集合中member的排名（按分值从小到大）


#### zrevrank key member 
获得集合中member的排名（按分值从大到小）


#### zrange key start end 
获得集合中指定区间成员，按分数递增排序


#### zrevrange key start end 
获得集合中指定区间成员，按分数递减排序


##### 应用场景
由于可以按照分值排序，所以适用于各种排行榜。比如：点击排行榜、销量排行榜、关注排行榜等。


**zset集合操作实现排行榜**
- 点击新闻：ZINCRBY hotNews:20230119 1 XXX的文化情缘

- 展示当日排行前十：ZREVRANGE hotNews:20230119 0 9 WITHSCORES

- 七日搜索榜单计算：ZUNIONSTORE hotNews:20230119-20230125 7

- 展示七日排行前十：ZREVRANGE hotNews:20230119-20230125 0 9 WITHSCORES

![](https://img2023.cnblogs.com/blog/1231979/202302/1231979-20230209151851932-146953868.png)




### hash类型(散列表)
Redis hash 是一个 string 类型的 field 和 value 的映射表，它提供了字段和字段值的映射。

![](https://img2023.cnblogs.com/blog/1231979/202302/1231979-20230209135519847-1428278489.png)


#### hset key field value
赋值，不区别新增或修改

#### hmset field1 value1 field2 value2	
批量赋值


#### hsetnx key field value
赋值，如果filed存在则不操作


#### hexists key filed
查看某个field是否存在


#### hget key field
获取一个字段值


#### hmget key field1 field2 ...
获取多个字段值

#### hgetall key
全选获取key所有的数据

#### hdel key field1 field2..
删除指定字段

#### hincrby key field increment
指定字段自增increment



#### hlen key

获得字段数量


##### 应用场景：可以做电商购物车
对象的存储 ，表数据的映射
电商购物车：

以用户id为key
商品id为field
商品数量为value

购物车操作：

添加商品：hset cart:100001 101007788 1
增加数量：hincrby cart:100001 101007788 1
商品总数：hlen cart:100001
删除商品：hdel cart:100001 101007788
获取购物车所有商品：hgetall cart:100001

![](https://img2023.cnblogs.com/blog/1231979/202302/1231979-20230209141217081-1217871790.png)




### bitmap位图类型

bitmap是进行位操作的
通过一个bit位来表示某个元素对应的值或者状态,其中的key就是对应元素本身。
bitmap本身会极大的节省储存空间。

![](https://img2023.cnblogs.com/blog/1231979/202302/1231979-20230209152125631-2112817362.png)


#### setbit key offset value 
设置key在offset处的bit值(只能是0或者1)。


#### getbit key offset 
获得key在offset处的bit值


#### bitcount key 
获得key的bit位为1的个数


#### bitpos key value 
返回第一个被设置为bit值的索引值


#### bitop and[or/xor/not] destkey key [key …]
对多个key 进行逻辑运算后存入destkey中




##### 应用场景
- 1.用户每月签到，用户id为key ， 日期作为偏移量 1表示签到
- 2、统计活跃用户, 日期为key，用户id为偏移量 1表示活跃
- 3、查询用户在线状态， 日期为key，用户id为偏移量 1表示在线


```script
127.0.0.1:6379> setbit user:sign:1000 20200101 1 #id为1000的用户20200101签到
(integer) 0
127.0.0.1:6379> setbit user:sign:1000 20200103 1 #id为1000的用户20200103签到
(integer) 0
127.0.0.1:6379> getbit user:sign:1000 20200101 #获得id为1000的用户20200101签到状态
1 表示签到
(integer) 1
127.0.0.1:6379> getbit user:sign:1000 20200102 #获得id为1000的用户20200102签到状态
0表示未签到
(integer) 0

127.0.0.1:6379> bitcount user:sign:1000 # 获得id为1000的用户签到次数
(integer) 2
127.0.0.1:6379> bitpos user:sign:1000 1 #id为1000的用户第一次签到的日期
(integer) 20200101
127.0.0.1:6379> setbit 20200201 1000 1 #20200201的1000号用户上线
(integer) 0
127.0.0.1:6379> setbit 20200202 1001 1 #20200202的1000号用户上线
(integer) 0
127.0.0.1:6379> setbit 20200201 1002 1 #20200201的1002号用户上线
(integer) 0
127.0.0.1:6379> bitcount 20200201 #20200201的上线用户有2个
(integer) 2
127.0.0.1:6379> bitop or desk1 20200201 20200202 #合并20200201的用户和20200202上线
了的用户
(integer) 126
127.0.0.1:6379> bitcount desk1 #统计20200201和20200202都上线的用
户个数
(integer) 3
```


### geo地理位置类型
geo是Redis用来处理位置信息的。在Redis3.2中正式使用。主要是利用了Z阶曲线、Base32编码和
geohash算法


#### geoadd key 经度 纬度 成员名称1 经度1 纬度1 成员名称2 经度2 纬度 2 ... 
添加地理坐标


#### geohash key 成员名称1 成员名称2... 
返回标准的geohash串


#### geopos key 成员名称1 成员名称2... 
返回成员经纬度


#### geodist key 成员1 成员2 单位 
计算成员间距离


#### georadiusbymember key 成员 值单位 count数 asc[desc]
根据成员查找附近的成员




##### 应用场景：
- 1、记录地理位置
- 2、计算距离
- 3、查找"附近的人"


```script
127.0.0.1:6379> geoadd user:addr 116.31 40.05 zhangf 116.38 39.88 zhaoyun 116.47
40.00 diaochan #添加用户地址 zhangf、zhaoyun、diaochan的经纬度
(integer) 3
127.0.0.1:6379> geohash user:addr zhangf diaochan #获得zhangf和diaochan的geohash码
1) "wx4eydyk5m0"
2) "wx4gd3fbgs0"
127.0.0.1:6379> geopos user:addr zhaoyun #获得zhaoyun的经纬度
1) 1) "116.38000041246414185"
2) "39.88000114172373145"
127.0.0.1:6379> geodist user:addr zhangf diaochan #计算zhangf到diaochan的距离,单
位是m
"14718.6972"
127.0.0.1:6379> geodist user:addr zhangf diaochan km #计算zhangf到diaochan的距离,
单位是km
"14.7187"
127.0.0.1:6379> geodist user:addr zhangf zhaoyun km
"19.8276"
127.0.0.1:6379> georadiusbymember user:addr zhangf 20 km withcoord withdist
count 3 asc
# 获得距离zhangf20km以内的按由近到远的顺序排出前三名的成员名称、距离及经纬度
#withcoord ： 获得经纬度 withdist：获得距离 withhash：获得geohash码
1) 1) "zhangf"
2) "0.0000"
3) 1) "116.31000012159347534"
2) "40.04999982043828055"
2) 1) "diaochan"
   2) "14.7187"
3) 1) "116.46999925374984741"
    2) "39.99999991084916218"
    3) 1) "zhaoyun"
    2) "19.8276"
    3) 1) "116.38000041246414185"
    2) "39.88000114172373145"
```



### stream数据流类型
stream是Redis5.0后新增的数据结构，用于可持久化的消息队列。

几乎满足了消息队列具备的全部内容，包括：
- 消息ID的序列化生成
- 消息遍历
- 消息的阻塞和非阻塞读取
- 消息的分组消费
- 未完成消息的处理
- 消息队列监控

![](https://img2023.cnblogs.com/blog/1231979/202302/1231979-20230209153319735-151030576.png)


>每个Stream都有唯一的名称，它就是Redis的key，首次使用 xadd 指令追加消息时自动创建。




### 跳跃表
跳跃表是有序集合（sorted-set）的底层实现，效率高，实现简单。

跳跃表的基本思想：将有序链表中的部分节点分层，每一层都是一个有序链表。





### 缓存过期和淘汰策略

Redis性能高：
官方数据
读：110000次/s
写：81000次/s
长期使用，key会不断增加，Redis作为缓存使用，物理内存也会满
内存与硬盘交换（swap） 虚拟内存 ，频繁IO 性能急剧下降



#### maxmemory
**不设置的场景**

- Redis的key是固定的，不会增加
- Redis作为DB使用，保证数据的完整性，不能淘汰 ， 可以做集群，横向扩展
- 缓存淘汰策略：禁止驱逐 （默认）



**设置的场景**

- Redis是作为缓存使用，不断增加Key
maxmemory ： 默认为0 不限制
问题：达到物理内存后性能急剧下架，甚至崩溃
内存与硬盘交换（swap） 虚拟内存 ，频繁IO 性能急剧下降
设置多少？ 与业务有关
1个Redis实例，保证系统运行 1 G ，剩下的就都可以设置Redis物理内存的3/4
slaver ： 留出一定的内存


在redis.conf中
```conf
maxmemory 1024mb
```

命令： 获得maxmemory数
CONFIG GET maxmemory

>设置maxmemory后，当趋近maxmemory时，通过缓存淘汰策略，从内存中删除对象

>不设置maxmemory 无最大内存限制 maxmemory-policy noeviction （禁止驱逐） 不淘汰
设置maxmemory maxmemory-policy 要配置