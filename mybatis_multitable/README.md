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
