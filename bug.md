### javax.validation.ValidationException: HV000183: Unable to load 'javax.el.ExpressionFactory'. Check that you have the EL dependencies on the classpath
添加下面的依赖
```
<dependency>
    <groupId>javax.el</groupId>
    <artifactId>javax.el-api</artifactId>
    <version>2.2.4</version>
</dependency>

<dependency>
    <groupId>org.glassfish.web</groupId>
    <artifactId>javax.el</artifactId>
    <version>2.2.4</version>
</dependency>
```



### Mail server connection failed; nested exception is javax.mail.MessagingException:
修改成下面的配置
```
spring:
  mail:
    host: smtp.qq.com
    # 默认端口号是465的
    port: 587
    username: 邮箱
    password: 授权码
    default-encoding: UTF-8
    properties:
      mail:
        smtp:
          socketFactory:
            class: javax.net.ssl.SSLSocketFactory

```



### Tomcat启动之后访问项目接口提示报错
```
The page you tried to access (/manager/dologin) does not exist.

The Manager application has been re-structured for Tomcat 7 onwards and some of URLs have changed. All URLs used to access the Manager application should now start with one of the following options:

/manager/html for the HTML GUI
/manager/text for the text interface
/manager/jmxproxy for the JMX proxy
/manager/status for the status pages
Note that the URL for the text interface has changed from "/manager" to "/manager/text".

You probably need to adjust the URL you are using to access the Manager application. However, there is always a chance you have found a bug in the Manager application. If you are sure you have found a bug, and that the bug has not already been reported, please report it to the Apache Tomcat team.
```

>Tomcat根目录最好不是manager开头的
>



### idea报错Failed to read candidate component class、No Spring WebApplicationInitializer types detected on classpath
今天idea导入一个新项目之后，启动之后出现了所下图所示的问题：     
- 1.1
![](https://img2023.cnblogs.com/blog/1231979/202304/1231979-20230421173136158-190993173.png)

- 1.2
![](https://img2023.cnblogs.com/blog/1231979/202304/1231979-20230421173234105-1680390943.png)

- 2
![](https://img2023.cnblogs.com/blog/1231979/202304/1231979-20230421173338303-1686876148.png)

>并且action包里面的action类打了断点没有一个勾只有一个普通红点


网上搜了一下原因可能是是JDK版本高了，因为项目中使用的是Spring 3，而我本地安装的是JDK 8，因此产生了兼容性的问题。（spring 3兼容jdk 7， spring 4兼容jdk 8）
![](https://img2023.cnblogs.com/blog/1231979/202304/1231979-20230421173849483-2002561820.png)

然后如下图设置一下即可  
![](https://img2023.cnblogs.com/blog/1231979/202304/1231979-20230421172859726-1848359974.png)

![](https://img2023.cnblogs.com/blog/1231979/202304/1231979-20230421172841343-686129939.png)

问题解决。 但是有一个地方需要注意一下： 如果Eclipse需要将java compiler改成了1.7


**运行成功并且界面可以显示后台也有正常的日志了**
![](https://img2023.cnblogs.com/blog/1231979/202304/1231979-20230421173717970-449285274.png)



### IE浏览器下载文件中文文件名乱码问题解决

```
if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
    fileName = URLEncoder.encode(fileName, "utf-8");
    fileName = fileName.replace("+", "%20");    //IE下载文件名空格变+号问题
} else {
    fileName = new String(fileName.getBytes(), "ISO8859-1");
}

```

### window.showmodaldialog is not a function 提示框无法打开
谷歌发布Chrome 37浏览器
Chrome 37禁用对showModalDialog的默认支持。
http://www.cfan.com.cn/2014/0827/110332.shtml

```
if(navigator.userAgent.indexOf("Chrome") >0 ){
    //url,window,
    return  window.open("login.do?"+"user=123",window,"dialogWidth=500px;dialogHeight=400px");
}
else{
    return window.showModalDialog("login.do?"+"user=123",window,"dialogWidth=500px;dialogHeight=400px");
}
```

![](https://img2023.cnblogs.com/blog/1231979/202304/1231979-20230419133844481-2109863876.png)


window.open方法传值
![](https://img2023.cnblogs.com/blog/1231979/202304/1231979-20230419133955943-73997278.png)
