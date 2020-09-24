## 第1章 课程整体概述与权限管理系统介绍

### 为什么需要权限管理
安全性：误操作、人为破坏、数据泄露等

数据隔离：不同的权限能看到及操作不同的数据

明确职责：运营、客服等不同角色,leader和dev等不同级别


### 数权限管理的核心
用户-权限：人员少,功能固定,或者特别简单的系统

RBAC（Role-Based Access Control）
用户-角色-权限,都适用
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200921145039215-1725178716.png)


### 理想中的权限管理
能实现角色级权限：RBAC

能实现功能级、数据级权限


### 相关操作界面
权限管理界面、角色管理界面、用户管理界面

角色和权限关系维护界面、用户和角色关系维护界面


### 开源权限管理项目
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200921145433357-1319474974.png)


## 第2章 Spring Security权限框架学习与演练
### Spring Security介绍

![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200921150548196-370849456.png)


### Spring Security权限拦截
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200921151024792-2100178125.png)

Security Context PersistenceFilter

LogoutFilter

AbstractAuthenticationProcessingFilter

DefaultLoginPageGeneratingFilter

BasicAuthenticationFilter

SecurityContextHolderAwareRequestFilter

RememberMeAuthenticationFilter

AnonymousAuthenticationFilter

SessionManagementFilter

ExceptionTranslationFilter

FilterSecurityInterceptor

FilterChainProxy

![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200921151800947-413697048.png)



### Spring Security数据库管理
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200921152419280-669777768.png)


代码实现：
UserDetailService接口
UserDetails接口
Authentication接口




### Spring Security权限缓存
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200921152840765-245761257.png)

代码实现：
CachingUserDetailService类



### Spring Security自定义决策
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200921152840765-245761257.png)


代码实现
RoleVoter类
AccessDecisionVoter接口
AffirmativeBased


### 搭建Spring Boot+Spring Security环境


![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200921155816837-444737744.png)


`默认账户：user 密码：查看控制台输出Using generated security password`
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200921155641220-587003465.png)

![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200921155853319-1877788525.png)



配置阿里云maven镜像
```xml
<mirrors>
	<mirror>
		<id>alimaven</id>
		<name>aliyun maven</name>
		<url>http://maven.aliyun.com/nexus/content/groups/public/</url>
		<mirrorOf>central</mirrorOf>
	</mirror>
</mirrors>
```


### 常见Case实现
只要能登录即可

需要某种角色限制访问



### Spring Security-优点
提供了一套安全框架,而且这个框架是可以用的

提供了很多用户认证的功能,实现相关接口即可,节约大量开发工作

基于spring,易于集成到spring项目中,且封装了许多方法



### Spring Security-缺点
配置文件多,角色被 "编码"到配置文件和源文件中,RBAC不明显

对于系统中用户、角色、权限之间的关系,没有可操作的界面

大数据量情况下,几乎不可用
