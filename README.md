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
`git clone https://github.com/legendHHH/SpringBootSelf.git`


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


### 2>&1到底是什么意思？
```
java -jar app.jar > app.log 2>&1 &
```
部署过项目的应该对这命令很熟悉，相信大部分人都知道>表示的是重定向，那么什么是重定向？2>&1又是什么意思？


#### 文件描述符原理
每个进程可以打开多个文件，所以每个进程都会有一个私有的文件描述符表（file descriptors table）。

下文称file descriptors table中的每一个条目为file descriptor，称file descriptor中的整数为fd。


文件描述符表的前3项已经被默认使用了。
- 0：标准输入（stdin）
- 1：标准输出（stdout）
- 2：标准错误（stderr）
![](https://img2022.cnblogs.com/blog/1231979/202209/1231979-20220930112625052-928719554.png)



`java -jar app.jar > app.log 2>&1 &`  这条指令的意思就是将app.jar程序用>运算符重定向标准输出，由原本的指向显示器改为app.log文件。

2>是用来重定向标准错误，因为标准错误在描述符表中的fd就是2，同样，其实重定向标准输出也可以表示为1>，不过一般简写为>。


标准错误和标准输出可以重定向到同一个地方，比如指令中的&1表示的就是标准输出，2>&1的含义就是重定向标准错误到标准输出表示的数据流中。



### Java接口忽然报错，错误信息是Out of sort memory, consider increasing server sort buffer size
字面意思就是 sort内存溢出，考虑增加服务器的排序缓冲区(sort_buffer_size)大小。
```
mysql> show variables like '%sort_buffer_size%';
+-------------------------+---------+
| Variable_name | Value |
+-------------------------+---------+
| innodb_sort_buffer_size | 1048576 |
| myisam_sort_buffer_size | 8388608 |
| sort_buffer_size | 262144 |
+-------------------------+---------+
3 rows in set (0.01 sec)


mysql> SET GLOBAL sort_buffer_size = 1024*1024;
```
>补充：EXPLAIN需要分析sql运行情况在分析原因



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

### Host '' is not allowed to connect to this MySQL server，如何解决？
```
在装有MySQL的机器上登录MySQL  mysql -u root -p password
执行 use mysql;
执行 update user set host = '%' where user = 'root'; #这一句执行完可能会报错，不用管它。
执行 FLUSH PRIVILEGES;


-- 非必须步骤
查看user表中的数据：select Host, User,Password from user;
修改user表中的Host：update user set Host='%' where User='root';


8.0版本
ALTER USER 'root'@'%' IDENTIFIED BY 'password' PASSWORD EXPIRE NEVER; #修改加密规则 ，'password'改成你的密码
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'password'; #更新一下用户的密码 ，'password'是你的密码
grant all privileges  on *.* to root@'%' identified by "password";
```
>第四步是刷新MySQL的权限相关表，一定不要忘了，我第一次的时候没有执行第四步，结果一直不成功，最后才找到这个原因


### 如果连接状态为 2003 可能造成出现的原因：
```
网络不通畅
mysql 服务未启动
防火墙未开放端口
```    

### 安装nvm后报错：Could not retrieve https://nodejs.org/dist/index.json. Get "https://nodejs.org/dist/index
在本地找到安装nvm的路径，在nvm文件夹下找到settings.txt,添加以下代码即可：
```
node_mirror:npm.taobao.org/mirrors/node/
 
npm_mirror:npm.taobao.org/mirrors/npm/
```


### 数字转化异常
![](https://img2022.cnblogs.com/blog/1231979/202207/1231979-20220726084146069-1650760539.png)

![](https://img2022.cnblogs.com/blog/1231979/202207/1231979-20220726084307622-1740064019.png)


调整后
![](https://img2022.cnblogs.com/blog/1231979/202207/1231979-20220726084328697-1654851605.png)



### docker中的tomcat服务器时区不正确修改
- 创建容器的时候设置时区
添加参数 -v /etc/localtime:/etc/localtime 启动容器
docker run -d -v /etc/localtime:/etc/localtime -p 8888:8080 tomcat:latest

- 启动后的容器修改时区
执行date 命令查看时间，会发现容器中的时间少了8个小时,这是由于时区不一致.


docker exec -it <容器名> /bin/bash
ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
docker restart <容器名>

![](https://img2022.cnblogs.com/blog/1231979/202207/1231979-20220726085315201-107565124.png)


复制主机的localtime（只是容器、宿主机时间同步）
docker cp /etc/localtime 753f856bca45:/etc/


### centos7安装node forever
- 安装Nodejs
- 安装forever
    - 进入项目路径：执行：npm install forever -g
    - /创建链接,使用forever时就不需要加入路径：ln -s /root/home/downloads/node-v5.2/bin/forever /usr/bin/forever


- 用forever：启动forever start xxx.js
- 重启：forever restart  xxx.js
- 停止：forever stop xxx.js


### idea svn配置报错:Can not use Subversion command line client
大致产生的原因是，开始安装svn的时候command line client没选中安装。

解决办法:
modify 一下svn
步骤:
1、找到你的svn安装包，双击，
2、之后选择(modify)修改安装
3、之后会看到command line client tools前面是个叉
4、点下叉，选择Entire feature will be installed on local hard drive5、然后就next, install就可以了。
注意:安装完后，再确定一下环境变量path里，有没有"C:\Program FilesTortoiseSVN\bin"如果有，就大功告成，只差最后一步了。
6、之后再按照IDEA提示 fix it

重启一下IDEA就不会报错了。


### Jenkins打包报错
```
Started by user XXX
Running as SYSTEM
[EnvInject] - Loading node environment variables.
Building in workspace /data/jenkins/workspace/iQueue/IQueue
Updating http://10.19.37.21/RD_Projects/Trunk/Src/iQueue at revision '2022-06-30T11:22:44.768 +0800' --quiet
Using sole credentials zhangzebin1/****** in realm ‘<http://10.19.37.21:80> Subversion repositories’
ERROR: Failed to update http://10.19.37.21/RD_Projects/Trunk/Src/iQueue
org.tmatesoft.svn.core.SVNException: svn: E204900: Can't open 'C:\WINDOWS\TEMP\report.tmp': 拒绝访问。  
svn: E175002: REPORT of '/RD_Projects/!svn/vcc/default': 500 Internal Server Error (http://10.19.37.21)
	at org.tmatesoft.svn.core.internal.wc.SVNErrorManager.error(SVNErrorManager.java:70)
	at org.tmatesoft.svn.core.internal.wc.SVNErrorManager.error(SVNErrorManager.java:57)
	at org.tmatesoft.svn.core.internal.io.dav.DAVRepository.runReport(DAVRepository.java:1363)
	at org.tmatesoft.svn.core.internal.io.dav.DAVRepository.update(DAVRepository.java:859)
	at org.tmatesoft.svn.core.internal.wc16.SVNUpdateClient16.update(SVNUpdateClient16.java:507)
	at org.tmatesoft.svn.core.internal.wc16.SVNUpdateClient16.doUpdate(SVNUpdateClient16.java:364)
	at org.tmatesoft.svn.core.internal.wc16.SVNUpdateClient16.doUpdate(SVNUpdateClient16.java:274)
	at org.tmatesoft.svn.core.internal.wc2.old.SvnOldUpdate.run(SvnOldUpdate.java:27)
	at org.tmatesoft.svn.core.internal.wc2.old.SvnOldUpdate.run(SvnOldUpdate.java:11)
	at org.tmatesoft.svn.core.internal.wc2.SvnOperationRunner.run(SvnOperationRunner.java:21)
	at org.tmatesoft.svn.core.wc2.SvnOperationFactory.run(SvnOperationFactory.java:1239)
	at org.tmatesoft.svn.core.wc2.SvnOperation.run(SvnOperation.java:294)
	at org.tmatesoft.svn.core.wc.SVNUpdateClient.doUpdate(SVNUpdateClient.java:311)
	at org.tmatesoft.svn.core.wc.SVNUpdateClient.doUpdate(SVNUpdateClient.java:291)
	at org.tmatesoft.svn.core.wc.SVNUpdateClient.doUpdate(SVNUpdateClient.java:387)
	at hudson.scm.subversion.UpdateUpdater$TaskImpl.perform(UpdateUpdater.java:159)
	at hudson.scm.subversion.WorkspaceUpdater$UpdateTask.delegateTo(WorkspaceUpdater.java:168)
	at hudson.scm.SubversionSCM$CheckOutTask.perform(SubversionSCM.java:1065)
	at hudson.scm.SubversionSCM$CheckOutTask.invoke(SubversionSCM.java:1041)
	at hudson.scm.SubversionSCM$CheckOutTask.invoke(SubversionSCM.java:1014)
	at hudson.FilePath.act(FilePath.java:1163)
	at hudson.FilePath.act(FilePath.java:1146)
	at hudson.scm.SubversionSCM.checkout(SubversionSCM.java:961)
	at hudson.scm.SubversionSCM.checkout(SubversionSCM.java:884)
	at hudson.scm.SCM.checkout(SCM.java:505)
	at hudson.model.AbstractProject.checkout(AbstractProject.java:1206)
	at hudson.model.AbstractBuild$AbstractBuildExecution.defaultCheckout(AbstractBuild.java:637)
	at jenkins.scm.SCMCheckoutStrategy.checkout(SCMCheckoutStrategy.java:86)
	at hudson.model.AbstractBuild$AbstractBuildExecution.run(AbstractBuild.java:509)
	at hudson.model.Run.execute(Run.java:1907)
	at hudson.maven.MavenModuleSetBuild.run(MavenModuleSetBuild.java:543)
	at hudson.model.ResourceController.execute(ResourceController.java:97)
	at hudson.model.Executor.run(Executor.java:429)
ERROR: Subversion update failed
org.tmatesoft.svn.core.SVNException: svn: E204900: Can't open 'C:\WINDOWS\TEMP\report.tmp': 拒绝访问。  
svn: E175002: REPORT of '/RD_Projects/iManager/!svn/vcc/default': 500 Internal Server Error (http://10.1.3.251)
	at org.tmatesoft.svn.core.internal.wc.SVNErrorManager.error(SVNErrorManager.java:70)
```

>新增一个权限账号即可


### MySQL server has gone away 报错
show global variables like 'max_allowed_packet';

+--------------------+---------+
| Variable_name      | Value   |
+--------------------+---------+
| max_allowed_packet | 4194304 |


可以看到默认情况下该项的大小只有4m，接下来将该值设置成150m（1024*1024*150） 
set global max_allowed_packet=157286400;


通过调大该值，一般来说再次导入数据量大的sql应该就能成功了，如果任然报错，则继续再调大一些就行，请注意通过在命令行中进行设置只对当前有效，重启mysql服务之后则恢复默认值，但可以通过修改配置文件（可以在配置文件my.cnf中添加max_allowed_packet=150M即可）来达到永久有效的目的，可其实我们并不是经常有这种大量数据的导入操作，所以个人觉得通过命令行使得当前配置生效即可，没有必要修改配置文件。



### Tomcat解决项目跨域问题（普通的web项目也不是ssm），Tomcat10的版本也支持下面的方式
防止项目请求接口跨域问题

- 第一种：在tomcat部署项目中，需要修改tomcat配置文件解决跨域。修改tomcat下面的conf/web.xml

![](https://img2022.cnblogs.com/blog/1231979/202211/1231979-20221110181004683-86386200.png)

在此文件下方新增（在<welcome-file-list>节点上面新增）
```
<filter>
  <filter-name>CorsFilter</filter-name>
  <filter-class>org.apache.catalina.filters.CorsFilter</filter-class>
  <init-param>
    <param-name>cors.allowed.origins</param-name>
    <param-value>*</param-value>
  </init-param>
</filter>
<filter-mapping>
  <filter-name>CorsFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>

```
或者在项目中进行修改，下载对应版本的tomcat-catalina的jar包

https://repo1.maven.org/maven2/org/apache/tomcat/tomcat-catalina/9.0.40/tomcat-catalina-9.0.40.jar

2、在WEB-INF/web.xml配置过滤器


```xml
<filter>  
    <filter-name>CorsFilter</filter-name>
    <filter-class>org.apache.catalina.filters.CorsFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>CorsFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```
 
 
```xml
<filter>  
  <filter-name>CorsFilter</filter-name>  
  <filter-class>org.apache.catalina.filters.CorsFilter</filter-class>  
  <init-param>  
    <param-name>cors.allowed.origins</param-name>  
    <param-value>*</param-value>  
  </init-param>  
  <init-param>  
    <param-name>cors.allowed.methods</param-name>  
    <param-value>GET,POST,HEAD,OPTIONS,PUT</param-value>  
  </init-param>  
  <init-param>  
    <param-name>cors.allowed.headers</param-name>  
    <param-value>Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers</param-value>  
  </init-param>  
  <init-param>  
    <param-name>cors.exposed.headers</param-name>  
    <param-value>Access-Control-Allow-Origin,Access-Control-Allow-Credentials</param-value>  
  </init-param>  
  <init-param>  
    <param-name>cors.support.credentials</param-name>  
    <param-value>true</param-value>  
  </init-param>  
  <init-param>  
    <param-name>cors.preflight.maxage</param-name>  
    <param-value>10</param-value>  
  </init-param>  
</filter>  
<filter-mapping>  
  <filter-name>CorsFilter</filter-name>  
  <url-pattern>/*</url-pattern>  
</filter-mapping>  
``` 

- 总结
spring CORS跨域请求解决方案总结：（建议采用方案1）

1、springboot CORS 跨域请求解决三大方案，springboot CorsFilter解决跨域问题

https://www.iteye.com/blog/fanshuyao-2517777

 

2、cors-filter使用，cors-filter解决跨域访问，cors-filter跨域请求

https://www.iteye.com/blog/fanshuyao-2517803

 

3、org.ebaysf.web的cors-filter使用，cors-filter跨域请求

https://www.iteye.com/blog/fanshuyao-2517820

 

4、java tomcat-catalina CorsFilter使用，apache tomcat-catalina CorsFilter使用

https://www.iteye.com/blog/fanshuyao-2517821


5、springboot jsonp 跨域请求，springboot使用jsonp跨域

https://www.iteye.com/blog/fanshuyao-2517789



#### tomcat10 带来的报错：jakarta.servlet.ServletException: 类com.kang.servlet.HelloServlet不是Servlet

下载cors-filter-2.9.jar，java-property-utils-1.9.jar这两个库文件，放到lib目录下。（可在

http://search.maven.org上查询并下载。）工程项目（如：test-demo）中web.xml中的配置如下：  

```xml
 <!--CORS 跨域资源访问-->
     <filter>
         <filter-name>CORS</filter-name>
         <filter-class>com.thetransactioncompany.cors.CORSFilter</filter-class>
         <init-param>
             <param-name>cors.allowGenericHttpRequests</param-name>
             <param-value>true</param-value>
         </init-param>
         <init-param>
             <param-name>cors.allowOrigin</param-name>
             <param-value>*</param-value>
         </init-param>
         <init-param>
             <param-name>cors.allowSubdomains</param-name>
             <param-value>false</param-value>
         </init-param>
         <init-param>
             <param-name>cors.supportedMethods</param-name>
             <param-value>GET, HEAD, POST, OPTIONS</param-value>
         </init-param>
         <init-param>
             <param-name>cors.supportedHeaders</param-name>
             <param-value>*</param-value>
         </init-param>
         <init-param>
             <param-name>cors.exposedHeaders</param-name>
             <param-value>X-Test-1, X-Test-2</param-value>
         </init-param>
         <init-param>
             <param-name>cors.supportsCredentials</param-name>
             <param-value>true</param-value>
         </init-param>
         <init-param>
             <param-name>cors.maxAge</param-name>
             <param-value>3600</param-value>
         </init-param>
     </filter>
     <filter-mapping>
         <filter-name>CORS</filter-name>
         <url-pattern>/*</url-pattern>
     </filter-mapping>
```


pom版本依赖
```
<!-- https://mvnrepository.com/artifact/jakarta.servlet/jakarta.servlet-api -->
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>5.0.0</version>
    <scope>provided</scope>
</dependency>

<!-- https://mvnrepository.com/artifact/jakarta.servlet.jsp/jakarta.servlet.jsp-api -->
<dependency>
    <groupId>jakarta.servlet.jsp</groupId>
    <artifactId>jakarta.servlet.jsp-api</artifactId>
    <version>3.0.0</version>
    <scope>provided</scope>
</dependency>
```



### eclipse部署项目会出现报错：Tomcat version 8.5 only supports J2EE 1.2, 1.3, 1.4, and Java EE 5, 6, and 7 Web modules
经过上网查询，这句话的意思是：Tomcat 8.5 版仅支持 J2EE 1.2、1.3、1.4 和 Java EE 5、6 和 7 Web 模块。

我们只需要进到eclipse打开项目下的 .setting 目录中，编辑 org.eclipse.wst.common.project.facet.core.xml 这个文件

将 <installed facet="jst.web" version="4.0"/> 这一项的版本号降低到 3.0 即可。（我的 Tomcat 为 8.5，Jdk 为 1.8）


### jenkins启动之后报错提示,因为在清除过期缓存条目后可用空间仍不足 - 请考虑增加缓存的最大空间

解决办法：在tomcat安装路径下找到conf/context.xml，在其中加入一条配置
```
<Resources cachingAllowed="true" cacheMaxSize="100000" />
```
![](https://img2022.cnblogs.com/blog/1231979/202208/1231979-20220802180324849-1919679316.png)



### 解压zip报错java.lang.IllegalArgumentException: MALFORMED
我解压的文件夹中包含中文名称，如 王小热热.png，这样解压完成后就会报错：java.lang.IllegalArgumentException: MALFORMED 

解决方案：
设置编码方式为GBK即可。
zipFile = new ZipFile(srcFile, Charset.forName("GBK"));


### MongoDB服务安装
cd MongoDB的bin目录下
mongod.exe --install --logpath=D:\software\MongoDB\mongodb-win32-x86_64-2008plus-ssl-3.6.11\log\MongoDBloglog.txt  --dbpath=D:\software\MongoDB\mongodb-win32-x86_64-2008plus-ssl-3.6.11\data --serviceName=MongoDB

![](https://img2022.cnblogs.com/blog/1231979/202207/1231979-20220727140423634-534753580.png)

### jenkins过滤版本，可选择版本


### 【基于yum安装的nodejs】卸载
1.自带工具删除
yum remove nodejs npm -y 

2.手动删除残留
进入 /usr/local/lib 删除所有 node 和 node_modules文件夹
进入 /usr/local/include 删除所有 node 和  node_modules 文件夹

进入 /usr/local/bin 删除 node 的可执行文件node和npm

检查 ~ 文件夹里面的"local" "lib"  "include"  文件夹，然后删除里面的所有  "node" 和  "node_modules" 文件夹

完成。


### Centos离线安装Nodejs
淘宝镜像源下载nodejs：https://npm.taobao.org/mirrors/node/v14.2.0/node-v14.2.0-linux-x64.tar.gz

解压：tar -xvf node-v14.2.0-linux-x64.tar.gz

配置环境变量
```
# 编辑环境变量,按i进入编辑模式，按Esc 输入wq回车后退出并保存
vi /etc/profile

# ----------将以下内容粘贴到合适的位置（最顶上/下都可以）---------
export NODE_HOME=/root/node-v14.2.0-linux-x64 # node目录
export PATH=$NODE_HOME/bin:$PATH

```

刷新环境变量
source /etc/profile





### MapStruct

编译报错`Error:(21, 1) java: Couldn't retrieve @Mapper annotation`
是因为swagge2里面引入了MapStruct的版本

#### 查找依赖树
mvn dependency:tree > maven.txt

mvn dependency:tree -Dverbose -Dincludes=org.mapstruct:mapstruct


#### 解决方法
exclude排除不同版本，使用相同版本的MapStruct。

`注意：Lombok版本大于1.18.16时，需要添加lombok-mapstruct-binding。`


- 添加依赖
```xml
<dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok</artifactId>
	<version>1.18.16</version>
</dependency>
<dependency>
	<groupId>org.mapstruct</groupId>
	<artifactId>mapstruct</artifactId>
	<version>1.4.1.Final</version>
</dependency>
```
 
- lombok<1.18.16时：
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.8.1</version>
    <configuration>
        <source>8</source>
        <target>8</target>
        <annotationProcessorPaths>
            <path>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
                <version>1.4.2.Final</version>
            </path>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.12</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

- lombok>1.18.16时：
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.8.1</version>
    <configuration>
        <source>11</source>
        <target>11</target>
        <annotationProcessorPaths>
            <path>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
                <version>1.4.2.Final</version>
            </path>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.12</version>
            </path>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok-mapstruct-binding</artifactId>
                <version>0.2.0</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```
>https://mapstruct.org/faq/#Why-do-I-get-this-error-Could-not-retrieve-Mapper-annotation-during-compilation


#### Error:(29, 15) java: Unknown property "gender" in result type StudentVO. Did you mean "null"?
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok-mapstruct-binding</artifactId>
    <version>0.2.0</version>
    <scope>provided</scope>
</dependency>
```


关于lombok和mapstruct的版本兼容问题，maven插件要使用3.6.0版本以上、lombok使用1.16.16版本以上，另外编译的lombok mapstruct的插件不要忘了。否则会出现下面的错误：No property named “aaa” exists in source parameter(s). Did you mean “null”?

这种异常就是lombok编译异常导致缺少get setter方法造成的。还有就是缺少构造函数也会抛异常。


<!-- idea 2018.1.1 之前的版本需要添加下面的配置，后期的版本就不需要了，可以注释掉 -->
```xml
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>${org.mapstruct.version}</version>
    <scope>provided</scope>
</dependency>

<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>3.8.1</version>
      <configuration>
        <source>1.8</source>
        <target>1.8</target>
        <annotationProcessorPaths>
          <path>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${org.projectlombok.version}</version>
          </path>
          <path>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-processor</artifactId>
            <version>${org.mapstruct.version}</version>
          </path>
        </annotationProcessorPaths>
      </configuration>
    </plugin>
  </plugins>
</build>
```

### ElasticSearch7设置用户名密码验证的登录
- 修改conf下面的elasticsearch.yml配置文件
```yml
xpack.security.enabled: true
xpack.license.self_generated.type: basic
xpack.security.transport.ssl.enabled: true
```

完整的yml配置文件
```yml
http.host: 0.0.0.0
http.cors.enabled: true
http.cors.allow-origin: "*"

xpack.security.enabled: true
xpack.license.self_generated.type: basic
xpack.security.transport.ssl.enabled: true
```

- 进入安装es的 bin/文件夹下 找到 elasticsearch-setup-passwords.bat
  或者打开cmd运行 `elasticsearch-setup-passwords interactive` 然后设置几个用户的密码
![](https://img2023.cnblogs.com/blog/1231979/202212/1231979-20221229193039413-772779007.png)


- 设置完验证结果
![](https://img2023.cnblogs.com/blog/1231979/202212/1231979-20221229193750112-1698385936.png)

![](https://img2023.cnblogs.com/blog/1231979/202212/1231979-20221229193851912-859894798.png)


- 修改项目的配置
```yml
spring: 
  data:
    # elasticsearch配置
    elasticsearch:
      # 开启es仓库
      repositories:
        enabled: true
      client:
        reactive:
          endpoints: 127.0.0.1:9200
          connection-timeout: 3000
          socket-timeout: 3000
          username: elastic
          password: 123456
  elasticsearch:
    rest:
      uris: 127.0.0.1:9200
      username: elastic
      password: 123456
```

 一般情况下，这个模糊查询是没有问题的，但是当testWord的字段过长时，会导致es搜索失效。通过资料查询，这个文本的范围在256~32766之间，如果是汉字查询，因为一个汉字占2个字节，还会在此基础上除2。


### Centos 中使用yum安装指定版本nodejs

- 设置版本
curl -sL https://rpm.nodesource.com/setup_14.x | sudo -E bash -

>setup_14.x 是对版本的设置，我需要的是14.x系列的最新版本。你可以指定具体的版本

- 执行安装
sudo yum install nodejs



### Jenkins 构建时间 相差 八小时 解决方法
地址栏输入：http://localhost:8099/script】或【系统管理-执行脚本命令】执行脚本命令行
```script
System.setProperty('org.apache.commons.jelly.tags.fmt.timeZone','Asia/Shanghai')
```


### IDEA实现远程DEBUG （jar包和war包）
#### jar包
启动参数上加上
```shell
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8327

完整===> nohup java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8327 app.jar  >D:/app.log 2>&1 &
```

#### war包
在tomcat的bin目录下的catalina.sh文件首行加上
```shell
CATALINA_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8427"
```
加完之后重启tomcat


### springboot的jar包大小优化
- 第一步：正常编译jar包，将lib文件夹解压出来。这时的pom.xml配置文件如下：
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <mainClass>com.example.DemoApplication</mainClass>
                <layout>ZIP</layout>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>repackage</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    <plugins>
<build>
```

进入项目根目录，执行命令：mvn clean install
将编译后的 Jar 包解压，拷贝 BOOT-INF 目录下的 lib 文件夹 到目标路径


- 第二步 修改pom.xml配置文件，使我们编译出来的jar包不包含lib：
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <mainClass>com.example.DemoApplication</mainClass>
                <layout>ZIP</layout>
                <includes>
                    <include>
                        <groupId>nothing</groupId>
                        <artifactId>nothing</artifactId>
                    </include>
                </includes>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>repackage</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    <plugins>
<build>
```

配置完成后，再次执行编译：mvn clean install

生成的 Jar 包体积明显变小，如下所示， 外部的 jar 包已经不会被引入了。


- 第三步 运行编译后的jar包

将 步骤 1 解压出来的 lib 文件夹、步骤 2 编译的 jar 包放在同一个目录, 运行下面命令：


java -Dloader.path=D:/lib -jar /path/to/demo-0.0.1-SNAPSHOT.jar
或者在 maven 中输入一下命令导出需要用到的 jar 包


mvn dependency:copy-dependencies -DoutputDirectory=D:\\ideaWork\\lib -DincludeScope=runtime



### rancher
mkdir -p /usr/local/docker/rancher-2.5.12/rancher
mkdir -p /usr/local/docker/rancher-2.5.12/log
mkdir -p /usr/local/docker/rancher-2.5.12/kubelet
mkdir -p /usr/local/docker/rancher-2.5.12/cni



docker run --privileged -d --restart=unless-stopped -p 8082:80 -p 8443:443 -v /usr/local/docker/rancher-2.5.12/rancher:/var/lib/rancher -v /usr/local/docker/rancher-2.5.12/log:/var/log -v /usr/local/docker/rancher-2.5.12/cni:/var/lib/cni -v /usr/local/docker/rancher-2.5.12/kubelet:/var/lib/kubelet --name rancher rancher/rancher:v2.5.12
