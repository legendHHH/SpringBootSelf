# Spring Boot 入门
## 1、Spring Boot 简介
> 简化Spring应用开发的一个框架；
>
> 整个Spring技术栈的一个大整合；
>
> J2EE开发的一站式解决方案；


## 2、微服务

2014，martin fowler

微服务：架构风格（服务微化）

一个应用应该是一组小型服务；可以通过HTTP的方式进行互通；

单体应用：ALL IN ONE

微服务：每一个功能元素最终都是一个可独立替换和独立升级的软件单元；

[详细参照微服务文档](https://martinfowler.com/articles/microservices.html#MicroservicesAndSoa)


## 环境准备
### 1、MAVEN设置；

给maven 的settings.xml配置文件的profiles标签添加

```xml
<profile>
  <id>jdk-1.8</id>
  <activation>
    <activeByDefault>true</activeByDefault>
    <jdk>1.8</jdk>
  </activation>
  <properties>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
  </properties>
</profile>
```
![](https://img2018.cnblogs.com/blog/1231979/201906/1231979-20190619082804896-607555100.png)


>开始第一个HelloWorld测试




## Git
### 获取项目到本地
`git clone https://github.com/qichunlin/SpringBootSelf.git`


### 新建本地仓库
`git init`

### 切换并在本地创建分支(默认都是master)
`git checkout -b dev`

`git checkout dev` ==>本地存在dev的分支可以直接切换


### 将新分支提交分支到远程
`git branch --set-upstream origin dev`


### 查看当前分支
`git branch`


### 删除本地已合并的分支
`git branch -d dev`


### 删除远程分支 
`git push origin --delete dev`

**注意: 在删除远程分支时，同名的本地分支并不会被删除，所以还需要单独删除本地同名分支**


### 分支模糊查找
`git branch | grep 'branchName'`


### 提交代码
`git status` ==> 查看代码变动有哪些

`git add . ` ==> 具体文件使用 `git add test.java`

`git commit -m '[ADD]提交信息'` 

`git push` ==>提交变更到远程


### 合并其他分支的代码(假设在master上需要合并dev分支全部的代码)
**注意：需要先切换到合并主分支,即master**

`git merge origin/dev`


### master只需要dev分支部分代码变动(某几个提交)
git cherry-pick命令的作用，就是将指定的提交(commit)应用于其他分支。

`git cherry-pick <HashA> <HashB>` ==>支持一次转移多个提交。

或者 

`git cherry-pick dev` ==> 表示转移该分支的最新提交


### 解决冲突
`git fetch`
`git pull`
`git diff`
`git stash`


### 本地分支关联远程分支(比如不小心取消了关联则使用下面的命令)
`git branch --set-upstream-to=origin/remote_branch your_branch`


### 分支重命名
#### 重命名git本地分支
`git branch -m old_local_branch_name new_local_branch_name`


#### 重命名git远程分支
`git branch -m old_local_branch_name new_local_branch_name` 重命名远程分支对应的本地分支

`git push origin :old_local_branch_name` 删除远程分支


`git push origin new_local_branch_name` 重新推送新命名的本地分支



### remote
`git remote rm name  # 删除远程仓库`
`git remote rename old_name new_name  # 修改仓库名`



### tag标签`
`git tag --list 列出所有的标签`

`git tag <your_tag_name> 创建一个标签`

`git tag -d <your_tag_name> 删除一个标签`

`git push --delete origin <your_tag_name> 删除远程仓库的标签`

`git push origin <your_tag_name> 推送一个标签到远程`

`git push origin --tags 推送多个本地标签到远程`



### git常见问题收录

#### 1. OpenSSL SSL_read: SSL_ERROR_SYSCALL, errno 10054

![](https://img2020.cnblogs.com/blog/1231979/202103/1231979-20210322135854054-1615411202.png)

>解决办法：主要原因是安全设置的问题.

首先执行 git config http.sslVerify "false"
若出现下列错误：git config http.sslVerify "false" fatal: not in a git directory

再继续执行 git config —global http.sslVerify "false"   问题解决



#### 2. SSH服务器拒绝了密码和登录Ubuntu只提示public key登录问题解决
安装：sudo apt install openssh-server

sudo vim /etc/ssh/sshd_config

#启用密码验证 
PasswordAuthentication yes

#关闭密钥验证 
RSAAuthentication no
PubkeyAuthentication no

![](https://img2020.cnblogs.com/blog/1231979/202107/1231979-20210702105319527-998506702.png)


/etc/init.d/ssh restart

![](https://img2020.cnblogs.com/blog/1231979/202107/1231979-20210702105519224-650858348.png)


lsa_release -a
![](https://img2020.cnblogs.com/blog/1231979/202107/1231979-20210702105630503-188950527.png)


### Centos7设置时区
- 查看系统时间
date

- 更改系统时间时区
timedatectl set-timezone Asia/Shanghai

- 可以重启后查看，防止重启后失效
reboot

date



### JDK安装(下载使用的免费账号记录)
2696671285@qq.com 
密码：Oracle123



cp jdk-8u291-linux-x64.tar.gz /usr/java1.8

cd /usr
tar -zxvf java1.8

vim /etc/profile

将光标移到最后一行，粘贴如下内容:
```profile
#java environment
export JAVA_HOME=/usr/jdk1.8.0_291
export CLASSPATH=.:${JAVA_HOME}/jre/lib/rt.jar:${JAVA_HOME}/lib/dt.jar:${JAVA_HOME}/lib/tools.jar
export PATH=$PATH:${JAVA_HOME}/bin

```

source /etc/profile

java -version



### IDEA配置连接虚拟机docker
-- 查看配置文件位置
systemctl show --property=FragmentPath docker

-- 修改配置文件
sudo vim /usr/lib/systemd/system/docker.service
    在该行添加如下内容：(这里端口为2375，所以后面在idea中连接时也要填写该端口)
     ExecStart=/usr/bin/dockerd -H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375

![](https://img2020.cnblogs.com/blog/1231979/202107/1231979-20210706133444912-423184107.png)


-- 重新加载配置文件
systemctl daemon-reload    


-- 重启docker
systemctl restart docker

-- 测试
 curl 远程服务器ip:2375/info
 curl 远程服务器ip:2375/version
 
 ![](https://img2020.cnblogs.com/blog/1231979/202107/1231979-20210706133320615-1674995652.png)



### 超时重试解决
- 引入GAV坐标
```xml
<dependency>
    <groupId>org.springframework.retry</groupId>
    <artifactId>spring-retry</artifactId>
    <version>1.1.2.RELEASE</version>
</dependency>
```

- 抛出RuntimeException异常后，继续重试，最多重试5次，每次在上一次的基础上延后1秒，multiplier为乘系数。若5次重试后依旧失败，则默认调用带有注解@Recover的方法，给接口返回一个默认值。
![](https://img2020.cnblogs.com/blog/1231979/202107/1231979-20210721101300196-711377172.png)


- 测试
```
2021-07-21 10:07:24.613 ERROR 18712 --- [nio-9909-exec-4] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.retry.ExhaustedRetryException: Cannot locate recovery method; nested exception is java.lang.RuntimeException: idd不能小于0] with root cause
```
![](https://img2020.cnblogs.com/blog/1231979/202107/1231979-20210721100745648-1524356201.png)


>解决方法：调用方法和恢复方法的返回值需要一致


### Docker-compose

Dockerfile 用来构建 Docker 镜像，那么 docker-compose 则是用来创建容器的。 

Docker 有三个主要的功能：Build、Ship 和 Run，使用 docker-compose 可以帮我们在 Run 的层面解决很多实际问题。docker-compose 通过一个 yaml 模板文件来统一管理多个容器的配置，如网络、数据卷、执行指令、环境变量、资源限制等等。有了 docker-compose 我们便可以一键重启、关闭、删除、监控所有的 docker 服务，只需要一次配置，则可以对容器进行统一管理，那么此时我们则不必为了每次要运行一堆容器时写大量的命令而头疼。


Docker Compose 是 Docker 官方编排（Orchestration）项目之一，负责快速在集群中部署分布式应用。 你可以也通过执行下面的命令，高速安装Docker Compose。

```
curl -L https://get.daocloud.io/docker/compose/releases/download/1.12.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose

chmod +x /usr/local/bin/docker-compose

docker-compose version # 查看版本号，测试是否安装成功

你可以通过修改URL中的版本，可以自定义您的需要的版本。
```


### 在局域网的两台电脑如何实现相互访问mysql数据库

![](https://img2020.cnblogs.com/blog/1231979/202108/1231979-20210809151008386-1873887475.png)


- 1.把mysql库中user表中的一条记录的Host字段值改为%,奇怪的是一定要用以下语句设置一下密码才行
- 2.update user set Password=PASSWORD("123456")WHERE Host="%";
- 3.执行flush privileges;命令使立即生效


```sql
INSERT INTO `mysql`.`user`(`Host`, `User`, `Password`, `Select_priv`, `Insert_priv`, `Update_priv`, `Delete_priv`, `Create_priv`, `Drop_priv`, `Reload_priv`, `Shutdown_priv`, `Process_priv`, `File_priv`, `Grant_priv`, `References_priv`, `Index_priv`, `Alter_priv`, `Show_db_priv`, `Super_priv`, `Create_tmp_table_priv`, `Lock_tables_priv`, `Execute_priv`, `Repl_slave_priv`, `Repl_client_priv`, `Create_view_priv`, `Show_view_priv`, `Create_routine_priv`, `Alter_routine_priv`, `Create_user_priv`, `Event_priv`, `Trigger_priv`, `Create_tablespace_priv`, `ssl_type`, `ssl_cipher`, `x509_issuer`, `x509_subject`, `max_questions`, `max_updates`, `max_connections`, `max_user_connections`, `plugin`, `authentication_string`, `password_expired`) VALUES ('%', 'root', '*6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'Y', 'N', '', '', '', '', 0, 0, 0, 0, 'mysql_native_password', '', 'N');

```
![](https://img2020.cnblogs.com/blog/1231979/202108/1231979-20210809151103530-1363879100.png)


### linux下上传和下载文件到远程服务器
scp -rp /path/filename username@remoteIP:/path #将本地文件拷贝到服务器上
scp -rp username@remoteIP:/path/filename /path #将远程文件从服务器下载到本地


### Linux下执行shell脚本，出现错误 $'\r':command not found的解决方案

这个错误是由于Windows系统和Linux系统的不同编码造成的。Windows下的回车是\r\n，而Linux下的回车是\n，所以用shell远程编写的脚本中的回车不被Linux系统识别，但使用vim编辑的时候还看不到\r。

>解决方法：用vim编辑脚本文件时加上-b，即`vim -b filename`，这样打开的文件是"Binary mode"，可以看到多出来的东西，显示的是^M，删掉保存运行就行了。


### 谷歌浏览器保留页面跳转前的请求
开发中往往会遇到这样的情况，调试一个请求，但是请求中有报错的跳转，结果一刷新页面就自动跳转了，也看不到请求报错，

>这个时候只要勾选上preserver log即可谷歌浏览器保留页面跳转前的请求


###  Linux下使用shell脚本启动，停止，重启服务
```
#!/bin/bash
#description: 启动重启server服务
#启动命令所在目录
HOME='/data/tsapp'
#过滤查询执行.jar的线程PID
pid=`ps -ef|grep TSApp.jar|grep -v grep|awk '{printf $2}'`
#执行jar
start(){
   if [ -n "$pid" ]; then
      echo "server already start,pid:$pid"
      return 0
   fi
   #进入命令所在目录
   cd $HOME
   #启动服务 把日志输出到HOME目录的server.log文件中
   nohup java -jar $HOME/TSApp.jar > $HOME/server.log 2>&1 &
   spid=`ps -ef|grep TSApp.jar|grep -v grep|awk '{printf $2}'`
   echo "program is start on pid:$spid"
}
#停止
stop(){
   if [ -z "$pid" ]; then
      echo "not find program on pid:$pid"
      return 0
   fi
   #结束程序，使用讯号2，如果不行可以尝试讯号9强制结束
   kill -9 $pid
   rm -rf $pid
   echo "kill program use signal 2,pid:$pid"
}
status(){
   if [ -z "$pid" ]; then
      echo "not find program on pid:$pid"
   else
      echo "program is running,pid:$pid"
   fi
}

case $1 in
   start)
      start
   ;;
   stop)
      stop
   ;;
   restart)
      $0 stop
      sleep 2
      $0 start
    ;;
   status)
      status
   ;;
   *)
      echo "Usage: {start|stop|status}"
   ;;
esac

exit 0
```

执行方法：给脚本加权限，
```
chmod 777 tsapp.sh

-- 启动jar
./tsapp.sh start

-- 停止jar
./tsapp.sh stop

-- 重新启动jar
./tsapp.sh restart

-- 查看jar运行状态
./tsapp.sh status
```

### centos7安装node forever
- 安装Nodejs
- 安装forever
    - 进入项目路径：执行：npm install forever -g
    - /创建链接,使用forever时就不需要加入路径：ln -s /root/home/downloads/node-v5.2/bin/forever /usr/bin/forever


- 用forever：启动forever start xxx.js
- 重启：forever restart  xxx.js
- 停止：forever stop xxx.js




### jenkins过滤版本，可选择版本
