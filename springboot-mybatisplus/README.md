### MyBatis-Plus
#### SQL注入原理
在MP中`ISqlInjector`负责SQL的注入工作，它是一个接口，AbstractSqlInjector是它的实现类
`com.baomidou.mybatisplus.core.injector.ISqlInjector`
`com.baomidou.mybatisplus.core.injector.AbstractSqlInjector`

![](https://img2022.cnblogs.com/blog/1231979/202208/1231979-20220807185401711-716166936.png)

```java
com.baomidou.mybatisplus.core.injector.AbstractSqlInjector.inspectInject

methodList.forEach((m) -> {
    m.inject(builderAssistant, mapperClass, modelClass, tableInfo);
});
```

```java
com.baomidou.mybatisplus.core.injector.AbstractMethod.inject

this.injectMappedStatement(mapperClass, modelClass, tableInfo);


com.baomidou.mybatisplus.core.injector.AbstractMethod.injectMappedStatement
```

![](https://img2022.cnblogs.com/blog/1231979/202208/1231979-20220807190528825-209775894.png)


例如：当前debug的是SelectById方法直接选择SelectById的实现类即可

`com.baomidou.mybatisplus.core.injector.methods.SelectById.injectMappedStatement`
``

[mybatisplus的官网配置模板](https://baomidou.com/pages/56bac0/#%E5%9F%BA%E6%9C%AC%E9%85%8D%E7%BD%AE)



#### configLocation  
[configLocation配置详情官网地址](https://baomidou.com/pages/56bac0/#configlocation)


mybatis配置文件位置，如果有单独的mybatis配置，将其路径配置到configLocation中
```xml
mybatis-plus.config-location=classpath:sqlMapConfig.xml
```


#### mapperLocations
[mapperLocations配置详情官网地址](https://baomidou.com/pages/56bac0/#configlocation)


mybatis配置文件位置，如果有单独的mybatis配置，将其路径配置到configLocation中
```xml
mybatis-plus.mapper-locations=classpath*:mapper/*.xml,classpath*:mapper/**/*.xml
```


#### mapUnderscoreToCamelCase
是否开启自动驼峰命名规则（camel case）映射，即从经典数据库列名 A_COLUMN（下划线命名） 到经典 Java 属性名 aColumn（驼峰命名） 的类似映射。

```xml
mybatis-plus.configuration.map-underscore-to-camel-case=true
```
![](https://img2022.cnblogs.com/blog/1231979/202208/1231979-20220808222331895-567915268.png)



>注意：该参数不能和mybatis-plus.config-location同时存在，会执行报错 Caused by: java.lang.IllegalStateException: Property 'configuration' and 'configLocation' can not specified with together
![](https://img2022.cnblogs.com/blog/1231979/202208/1231979-20220808222831156-1225950009.png)


