技术选型就是 SpringBoot+Websocket+jsch+xterm.js


- 使用vargant快速创建虚拟机
$ vagrant init      # 初始化

$ vagrant up        # 启动虚拟机
$ vagrant halt      # 关闭虚拟机
$ vagrant reload    # 重启虚拟机
$ vagrant ssh       # SSH 至虚拟机
$ vagrant suspend   # 挂起虚拟机
$ vagrant resume    # 唤醒虚拟机
$ vagrant status    # 查看虚拟机运行状态
$ vagrant destroy   # 销毁当前虚拟机


box管理命令
$ vagrant box list    # 查看本地box列表
$ vagrant box add     # 添加box到列表

$ vagrant box remove  # 从box列表移除 


- 前端使用xterm命令终端工具
xterm 是一个使用 TypeScript 编写的前端终端组件


- JSch 处理命令
JSch库可以实现Java连接Linux服务器并操作命令、文件等，支持常用的各种授权模式。网址：http://www.jcraft.com/jsch/