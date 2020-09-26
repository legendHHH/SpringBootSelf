## 第4章 权限管理系统核心表设计

### 为什么要自己写？
◆满足框架的要求进行配置
◆没有界面操作和查看
◆期望更细致的管理



### 基本目标
◆基于扩展的RBAC实现
◆易于扩展，能灵活适应需求的变化
◆所有管理都有界面方便操作



◆需要开发功能确定
◆详细表结构设计
◆编码实现
◆详细表结构设计



![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200924103254303-751122892.png)


![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200924103309891-1610055885.png)



### 配置管理类功能
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200924103502292-582763138.png)


### 权限拦截类功能
◆在切面（Filter）做权限拦截
◆确定用户是否拥有某个权限


### 辅助类功能
·缓存（Redis）的封装和使用
·各种树：部门树、权限模块树、角色权限树、用户权限树
权限操作恢复



### 详细表结构设计
用户表、部门表

权限表、权限模块表

角色相关表

权限相关更新日志记录表




项目启动注意事项：

1、数据库配置：/resources/settings.properties
2、redis配置：/resources/redis.properties
3、项目登录页：/signin.jsp
4、登录使用用户名和密码：
username: admin@qq.com
password: 12345678

其他：
1、如果暂时不想使用redis，如何移除
1) applicationContext.xml里移除 <import resource="redis.xml" />
2) 修改RedisPool.java 类取消被spring管理
3）修改SysCacheService.java 类移除RedisPool.java的使用

2、如果想在正式环境使用，需要注意哪些
1）如果是集群部署，需要配置session共享，保证登录一次就可以，体验好
可以参考一下：http://blog.csdn.net/pingnanlee/article/details/68065535
2）确认一下项目里超级管理员的权限的规则
代码位置：SysCoreService.java类里的isSuperAdmin()
3）新增管理员的密码处理
SysUserService.java里的save() 方法里需要移除 password = "12345678";
同时，MailUtil里的发信参数要补全，并在SysUserService.java里的save()里 sysUserMapper.insertSelective(user) 之前调用
这是默认给的逻辑，可以根据项目实际情况调整