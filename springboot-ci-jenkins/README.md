## Jenkins

https://www.jenkins.io/zh/doc/book/installing/

![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912100953399-2088216830.png)

tar -zxvf apache-tomcat-7.0.75.tar.gz
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912101022472-899103935.png)


修改server.xml
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912101201105-649551458.png)


修改 tomcat-user.xml
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912101231710-1852413373.png)

启动Tomcat

![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912100818389-157710961.png)

进去jenkins
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912100838070-703320977.png)

获取初始密码
cat /root/.jenkins/secrets/initialAdminPassword

![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912101410678-850977210.png)


![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912101540097-1442385551.png)

![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912102804523-1021761753.png)


![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912102528184-526122997.png)


详细的Jenkins的镜像地址查询
http://mirrors.jenkins-ci.org/status.html


选择【高级】选项卡替换最下方【升级站点】中的URL
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912103528170-1735662334.png)

将

http://updates.jenkins-ci.org/update-center.json
替换为

http://mirror.esuni.jp/jenkins/updates/update-center.json



https://jenkins.io/download/

https://pkg.jenkins.io/redhat-stable/

下载最新的war包
http://mirrors.jenkins.io/war-stable/latest/jenkins.war


https://blog.csdn.net/u013053075/article/details/101770152




![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912130256312-1420253367.png)
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912130308540-147569876.png)



![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912135546038-1958262815.png)
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912135603781-696974580.png)
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912135652628-9334159.png)
![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912140152973-697654523.png)



![](https://img2020.cnblogs.com/blog/1231979/202009/1231979-20200912141506192-247234482.png)
