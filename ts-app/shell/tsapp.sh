#!/bin/bash
#description: 启动重启server服务
#启动命令所在目录
HOME='/home/aibot'
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
   #启动服务(指定生产配置文件) 把日志输出到HOME目录的server.log文件中
   nohup java -jar $HOME/TSApp.jar --spring.profiles.active=prod > $HOME/server.log 2>&1 &
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