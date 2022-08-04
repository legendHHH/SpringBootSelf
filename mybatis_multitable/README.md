### MyBatis的复杂映射

#### 一对一查询
查询一个订单，与此同时查询出该订单所属的用户(内连接)

使用`association`标签，
property：指的是实体类的对象的属性名。
javaType：java bean的类型

```xml
<resultMap id="orderMap" type="com.legend.pojo.Order">
    <id property="id" column="id" />
    <result property="orderTime" column="orderTime" />
    <result property="total" column="total" />

    <!-- 一对一 集合使用的标签 -->
    <association property="user" javaType="com.legend.pojo.User">
        <id property="id" column="uid" />
        <result property="username" column="username" />
    </association>
</resultMap>
```

#### 一对多查询
查询全部用户,与此同时查询出每个用户具有的订单信息

使用`collection`标签，
property：指的是实体类的对象的属性名。
ofType：List集合的泛型java类型
```xml
<resultMap id="userMap" type="com.legend.pojo.User">
    <id property="id" column="id"></id>
    <result property="username" column="username"></result>

    <!--一对多 集合使用的标签-->
    <collection property="orderList" ofType="com.legend.pojo.Order">
        <id property="id" column="oid"></id>
        <result property="orderTime" column="orderTime"></result>
        <result property="total" column="total"></result>
    </collection>
</resultMap>
```

#### 多对多查询
查询所有用户同时查询出每个用户的所有角色
SELECT * FROM user u LEFT JOIN sys_user_role ur ON ur.userid=u.id LEFT JOIN sys_role r ON r.id=ur.roleid

多对多相当于两个一对多

使用`collection`标签，
property：指的是实体类的对象的属性名。
ofType：List集合的泛型java类型

```xml
<resultMap id="userRoleMap" type="com.legend.pojo.User">
    <id property="id" column="userid" />
    <result property="username" column="username" />
    <result property="password" column="password" />

    <!--多对多 集合使用的标签-->
    <collection property="roleList" ofType="com.legend.pojo.Role">
        <id property="id" column="roleid" />
        <result property="roleName" column="roleName" />
        <result property="roleDesc" column="roleDesc" />
    </collection>
</resultMap>
```

#### MyBatis常用注解
@Insert:实现新增
@Update:实现更新
@Delete:实现删除
@Select:实现查询



### Mybatis缓存
一级缓存：底层是一个HashMap， 
         cacheKey是由statementId、parameter、boundSql、rowBounds
         value：user对象，是从数据库中查询出来的结果


首先是去一级缓存中去查询：有：直接返回
                        没有：查询数据库，同时将查询出来的结果存到一级缓存中
                        
                     
结论：做增删改操作，并进行了事务提交，就是刷新了一级缓存                        
clearCache(); 手动刷新一级缓存


#### Mybatis缓存底层源码分析

##### 一级缓存到底是什么？HashMap底层结构
![](https://img2022.cnblogs.com/blog/1231979/202208/1231979-20220803184523847-1734348332.png)



##### 一级缓存什么时候被创建


##### 一级缓存的⼯作流程是怎样的？



开启了二级缓存后，还需要将要缓存的pojo实现Serializable接口，为了将缓存数据取出执行反序列化操作，
因为二级缓存数据存储介质多种多样，不一定只存在内中，有可能存在硬盘中，如果我们要再取这个缓存的话，
就需要反序列化了。所以mybatis中的pojo都去实现Serializable




#### useCache和flushCache
mybatis中还可以配置userCache和flushCache等配置项，userCache是用来设置是否禁用二级缓存的，
在statement中设置useCache=false可以禁用当前select语句的二级缓存，即每次查询都会发出 sql
去查询，默认情况是true,即该sql使用二级缓存

flushCache="true"属性，默认情况下为true,即刷新缓存，如果改成false则 不
会刷新。使⽤缓存时如果⼿动修改数据库表中的查询数据会出现脏读。       