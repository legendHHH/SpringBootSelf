### TSApp服务启动说明
- 1.将打包好的jar包、以及tsapp.sh两个文件放进 `/data/tsapp` 目录下。
- 2.使用 `vim -b tsapp.sh` 命令检查脚本是否存在 ^M 的字符,如果有删除即可。防止执行脚本失败
- 3.启动服务(tsapp默认端口是8080) 
    - 启动服务：sh tsapp.sh start
    - 查询服务状态：sh tsapp.sh status 
    - 停止服务：sh tsapp.sh stop