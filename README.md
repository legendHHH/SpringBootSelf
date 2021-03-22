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