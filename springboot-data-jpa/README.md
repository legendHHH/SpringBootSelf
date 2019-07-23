# Springboot数据访问

## 4 整合SpringData JPA
### 4.1 SpringData简介
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190723090817960-491482062.png)


### 4.2 整合SpringData JPA
JPA:ORM（Object Relational Mapping）;

`编写实体类做映射`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190723090927125-297076525.png)

`编写接口访问数据`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190723091213574-1405370538.png)

`测试`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190723093913876-1120253028.png)



![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190723093013026-1073148125.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190723093049017-216162605.png)

`springboot集成JPA返回Json报错 com.fasterxml.jackson.data`

#### 解决办法
`在配置文件中添加时区`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190723093942711-794048554.png)


`在实体类上添加注解 @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })`

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190723093333350-812077233.png)

`插入数据`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190723093749755-209367956.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190723093650253-1633201681.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190723093726448-78343799.png)
