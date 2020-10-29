首次接口调用控制台执行会报错(因为MongoDB数据库没有用户)
(AuthenticationFailed): 'Authentication failed.' on server localhost:27017.

![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201029211023561-2041673590.png)

给MongoDB的admin数据库创建一个用户

// 给数据库授权
use admin

```
db.createUser({
    user: "root",
    pwd: "123456",
    roles: [
        {
            role: "dbAdmin",
            db: "admin"
        },
        {
            role: "readWrite",
            db: "admin"
        }
    ],
    authenticationRestrictions: [ ]
});

```



http://localhost:8091/mongo/save
```json
{
	"id":"1",
	"name":"legend",
	"price":"12",
	"info":"666",
	"publish":"7出版社"
}
```

![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201029210844698-152131424.png)

![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201029210952926-400435937.png)




http://localhost:8091/mongo/findAll
![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201029212747308-1150140498.png)



- MongoDB默认的数据库不展示被隐藏起来了
![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201029212029359-442883292.png)

点击查看 选择显示隐藏的项目
![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201029212248106-714947441.png)

![](https://img2020.cnblogs.com/blog/1231979/202010/1231979-20201029212307394-683554336.png)



