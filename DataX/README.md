## 大数据技术之DataX
### 一、概述
#### 1.1 什么是DataX
DataX是阿里巴巴开源的一个异构数据源离线同步工具，致力于实现包括关系型数据库(MySQL、Oracle等)、HDFS、Hive、ODPS、HBase、FTP等各种异构数据源之间稳定高效的数据同步功能。-


#### 1.2 DataX的设计
为了解决异构数据源同步问题，DataX将复杂的网状的同步链路变成了星型数据链路，DataX作为中间传输载体负责连接各种数据源。当需要接入一个新的数据源的时候，只需要将此数据源对接到DataX，便能跟已有的数据源做到无缝数据同步。 

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224084802373-1171761177.png)

>可以是插件式的

#### 1.3 支持的数据源
DataX目前已经有了比较全面的插件体系，主流的RDBMS数据库、NOSQL、大数据计算机系统都已经接入

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224085332586-629137364.png)


#### 1.4 框架设计
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224085417703-101336628.png)

- Reader：数据采集模块，负责采集数据源的数据，将数据发送给Framework 。
- Writer：数据写入模块，负责不断向Framework取数据，并将数据写入到目的端。
- Framework：用于连接reader和writer，作为两者的数据传输通道，并处理缓冲，流控，并发，数据转换等核心技术问题。


#### 1.5 运行原理
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224085819963-1928370371.png)

- Job：单个作业的管理节点，负责数据清理、子任务划分、TaskGroup监控管理。
- Task：由Job切分而来，是DataX作业的最小单元，每个Task负责一部分数据的同步工作。
- Schedule：将Task组成TaskGroup，单个TaskGroup的并发数量为5。
- TaskGroup：负责启动Task


```
举例来说，用户提交了一个Datax作业，并且配置了20个并发,目的是将一个100张分表的mysql数据同步到odps里面。DataX的调度决策思路是:
1)DataXJob根据分库分表切分成了100个Task。
2）根据20个并发，DataX计算共需要分配4个TaskGroup。
3)4个TaskGroup平分切分好的100个Task，每一个TaskGroup负责以5个并发共计运行25个Task。
```


#### 1.6 与Sqoop的对比
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224090449189-345593772.png)


>带宽100Mbps  ==》 速率是 100/8 =12.5m/s
>DataX更适合各种各样的数据源处理


### 二、快速入门
#### 2.1 官方地址
[DataX软件下载地址](http://datax-opensource.oss-cn-hangzhou.aliyuncs.com/datax.tar.gz)

[DataX文档阅读地址](https://github.com/alibaba/DataX)



#### 2.2 前置要求
- Linux
- JDK(1.8以上,推荐1.8)
- Python(推荐Python2.6.X)


#### 2.3 安装
```
tar -zxvf datax.tar.gz -C /opt/module

cd /opt/module/datax

python bin/datax.py ./job/job.json
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224092612617-1675836214.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224095055937-318640008.png)


- 初始化自检脚本报错
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224095908009-1442653983.png)

>把reader和writer下的._*删了就行

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224100337609-1511021096.png)
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224100355894-1149572171.png)


![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224100045553-2059836649.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224100023882-2016179144.png)


- DataX启动log日志
```
[root@localhost datax]# python bin/datax.py ./job/job.json

DataX (DATAX-OPENSOURCE-3.0), From Alibaba !
Copyright (C) 2010-2017, Alibaba Group. All Rights Reserved.


2021-12-24 09:59:57.990 [main] INFO  VMInfo - VMInfo# operatingSystem class => sun.management.OperatingSystemImpl
2021-12-24 09:59:58.013 [main] INFO  Engine - the machine info  => 

	osInfo:	Oracle Corporation 1.8 25.291-b10
	jvmInfo:	Linux amd64 3.10.0-1160.31.1.el7.x86_64
	cpu num:	2

	totalPhysicalMemory:	-0.00G
	freePhysicalMemory:	-0.00G
	maxFileDescriptorCount:	-1
	currentOpenFileDescriptorCount:	-1

	GC Names	[PS MarkSweep, PS Scavenge]

	MEMORY_NAME                    | allocation_size                | init_size                      
	PS Eden Space                  | 256.00MB                       | 256.00MB                       
	Code Cache                     | 240.00MB                       | 2.44MB                         
	Compressed Class Space         | 1,024.00MB                     | 0.00MB                         
	PS Survivor Space              | 42.50MB                        | 42.50MB                        
	PS Old Gen                     | 683.00MB                       | 683.00MB                       
	Metaspace                      | -0.00MB                        | 0.00MB                         


2021-12-24 09:59:58.062 [main] INFO  Engine - 
{
	"content":[
		{
			"reader":{
				"name":"streamreader",
				"parameter":{
					"column":[
						{
							"type":"string",
							"value":"DataX"
						},
						{
							"type":"long",
							"value":19890604
						},
						{
							"type":"date",
							"value":"1989-06-04 00:00:00"
						},
						{
							"type":"bool",
							"value":true
						},
						{
							"type":"bytes",
							"value":"test"
						}
					],
					"sliceRecordCount":100000
				}
			},
			"writer":{
				"name":"streamwriter",
				"parameter":{
					"encoding":"UTF-8",
					"print":false
				}
			}
		}
	],
	"setting":{
		"errorLimit":{
			"percentage":0.02,
			"record":0
		},
		"speed":{
			"byte":10485760
		}
	}
}

2021-12-24 09:59:58.099 [main] WARN  Engine - prioriy set to 0, because NumberFormatException, the value is: null
2021-12-24 09:59:58.102 [main] INFO  PerfTrace - PerfTrace traceId=job_-1, isEnable=false, priority=0
2021-12-24 09:59:58.102 [main] INFO  JobContainer - DataX jobContainer starts job.
2021-12-24 09:59:58.107 [main] INFO  JobContainer - Set jobId = 0
2021-12-24 09:59:58.152 [job-0] INFO  JobContainer - jobContainer starts to do prepare ...
2021-12-24 09:59:58.153 [job-0] INFO  JobContainer - DataX Reader.Job [streamreader] do prepare work .
2021-12-24 09:59:58.155 [job-0] INFO  JobContainer - DataX Writer.Job [streamwriter] do prepare work .
2021-12-24 09:59:58.156 [job-0] INFO  JobContainer - jobContainer starts to do split ...
2021-12-24 09:59:58.158 [job-0] INFO  JobContainer - Job set Max-Byte-Speed to 10485760 bytes.
2021-12-24 09:59:58.160 [job-0] INFO  JobContainer - DataX Reader.Job [streamreader] splits to [1] tasks.
2021-12-24 09:59:58.161 [job-0] INFO  JobContainer - DataX Writer.Job [streamwriter] splits to [1] tasks.
2021-12-24 09:59:58.250 [job-0] INFO  JobContainer - jobContainer starts to do schedule ...
2021-12-24 09:59:58.264 [job-0] INFO  JobContainer - Scheduler starts [1] taskGroups.
2021-12-24 09:59:58.275 [job-0] INFO  JobContainer - Running by standalone Mode.
2021-12-24 09:59:58.328 [taskGroup-0] INFO  TaskGroupContainer - taskGroupId=[0] start [1] channels for [1] tasks.
2021-12-24 09:59:58.343 [taskGroup-0] INFO  Channel - Channel set byte_speed_limit to -1, No bps activated.
2021-12-24 09:59:58.344 [taskGroup-0] INFO  Channel - Channel set record_speed_limit to -1, No tps activated.
2021-12-24 09:59:58.380 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] taskId[0] attemptCount[1] is started
2021-12-24 09:59:58.582 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] taskId[0] is successed, used[208]ms
2021-12-24 09:59:58.583 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] completed it's tasks.
2021-12-24 10:00:08.348 [job-0] INFO  StandAloneJobContainerCommunicator - Total 100000 records, 2600000 bytes | Speed 253.91KB/s, 10000 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 0.111s |  All Task WaitReaderTime 0.151s | Percentage 100.00%
2021-12-24 10:00:08.348 [job-0] INFO  AbstractScheduler - Scheduler accomplished all tasks.
2021-12-24 10:00:08.349 [job-0] INFO  JobContainer - DataX Writer.Job [streamwriter] do post work.
2021-12-24 10:00:08.350 [job-0] INFO  JobContainer - DataX Reader.Job [streamreader] do post work.
2021-12-24 10:00:08.350 [job-0] INFO  JobContainer - DataX jobId [0] completed successfully.
2021-12-24 10:00:08.352 [job-0] INFO  HookInvoker - No hook invoked, because base dir not exists or is a file: /root/datax/hook
2021-12-24 10:00:08.355 [job-0] INFO  JobContainer - 
	 [total cpu info] => 
		averageCpu                     | maxDeltaCpu                    | minDeltaCpu                    
		-1.00%                         | -1.00%                         | -1.00%
                        

	 [total gc info] => 
		 NAME                 | totalGCCount       | maxDeltaGCCount    | minDeltaGCCount    | totalGCTime        | maxDeltaGCTime     | minDeltaGCTime     
		 PS MarkSweep         | 0                  | 0                  | 0                  | 0.000s             | 0.000s             | 0.000s             
		 PS Scavenge          | 0                  | 0                  | 0                  | 0.000s             | 0.000s             | 0.000s             

2021-12-24 10:00:08.357 [job-0] INFO  JobContainer - PerfTrace not enable!
2021-12-24 10:00:08.359 [job-0] INFO  StandAloneJobContainerCommunicator - Total 100000 records, 2600000 bytes | Speed 253.91KB/s, 10000 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 0.111s |  All Task WaitReaderTime 0.151s | Percentage 100.00%
2021-12-24 10:00:08.361 [job-0] INFO  JobContainer - 
任务启动时刻                    : 2021-12-24 09:59:58
任务结束时刻                    : 2021-12-24 10:00:08
任务总计耗时                    :                 10s
任务平均流量                    :          253.91KB/s
记录写入速度                    :          10000rec/s
读出记录总数                    :              100000
读写失败总数                    :                   0

[root@localhost datax]# 
```



#### DataX源码安装可能会遇到的问题
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211222093414956-1694878452.png)
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211222093535343-2036660429.png)
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211222231700276-232011626.png)




![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211222105139532-399099631.png)


https://blog.csdn.net/weixin_36142042/article/details/105547682
https://blog.csdn.net/weixin_42786042/article/details/104962402

https://public.nexus.pentaho.org/
https://javalibs.com/

- 两个jar包的下载地址
**https://public.nexus.pentaho.org/repository/proxy-public-3rd-party-release/eigenbase/eigenbase-properties/1.1.4/eigenbase-properties-1.1.4.jar**
**https://public.nexus.pentaho.org/repository/proxy-public-3rd-party-release/org/pentaho/pentaho-aggdesigner-algorithm/5.1.5-jhyde/pentaho-aggdesigner-algorithm-5.1.5-jhyde.jar**




### 三、使用案例
#### 3.1 从stream流读取数据并打印到控制台
```
-- 查看模板文件
bin/datax.py -r streamreader -w streamwriter

-- 执行自定义的job
bin/datax.py ./job/stream2streamjob.json
```


- 输入上述的命令后会打印job的模板
```
{
    "job": {
        "content": [
            {
	            -- 读
                "reader": {
                    "name": "streamreader",
                    "parameter": {
	                    -- 指定列数据信息
                        "column": [],
                        -- 产生的条数
                        "sliceRecordCount": ""
                    }
                },
                -- 写
                "writer": {
                    "name": "streamwriter",
                    "parameter": {
	                    -- 字符集编码
                        "encoding": "",
                        -- 是否在控制台打印
                        "print": true
                    }
                }
            }
        ],
        "setting": {
            "speed": {
	            -- 并发数
                "channel": ""
            }
        }
    }
}
```

- 自定义调整job的模板信息
```
{
    "job": {
        "content": [
            {
                "reader": {
                    "name": "streamreader", 
                    "parameter": {
                        "column": [
							{
								"type":"string",
								"value":"zhangsan"
							},
							{
								"type":"string",
								"value":"18"
							}
						], 
                        "sliceRecordCount": "10"
                    }
                }, 
                "writer": {
                    "name": "streamwriter", 
                    "parameter": {
                        "encoding": "UTF-8", 
                        "print": true
                    }
                }
            }
        ], 
        "setting": {
            "speed": {
                "channel": "1"
            }
        }
    }
}
```

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224102616555-96979131.png)


- datax启动的时候会先检查json的文件、以及支持的数据类型
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224102939679-1770319336.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224103426782-335487554.png)


- datax启动自定义的job的日志
```
[root@localhost datax]# bin/datax.py ./job/stream2streamjob.json 

DataX (DATAX-OPENSOURCE-3.0), From Alibaba !
Copyright (C) 2010-2017, Alibaba Group. All Rights Reserved.


2021-12-24 10:33:32.865 [main] INFO  VMInfo - VMInfo# operatingSystem class => sun.management.OperatingSystemImpl
2021-12-24 10:33:32.878 [main] INFO  Engine - the machine info  => 

	osInfo:	Oracle Corporation 1.8 25.291-b10
	jvmInfo:	Linux amd64 3.10.0-1160.31.1.el7.x86_64
	cpu num:	2

	totalPhysicalMemory:	-0.00G
	freePhysicalMemory:	-0.00G
	maxFileDescriptorCount:	-1
	currentOpenFileDescriptorCount:	-1

	GC Names	[PS MarkSweep, PS Scavenge]

	MEMORY_NAME                    | allocation_size                | init_size                      
	PS Eden Space                  | 256.00MB                       | 256.00MB                       
	Code Cache                     | 240.00MB                       | 2.44MB                         
	Compressed Class Space         | 1,024.00MB                     | 0.00MB                         
	PS Survivor Space              | 42.50MB                        | 42.50MB                        
	PS Old Gen                     | 683.00MB                       | 683.00MB                       
	Metaspace                      | -0.00MB                        | 0.00MB                         


2021-12-24 10:33:32.913 [main] INFO  Engine - 
{
	"content":[
		{
			"reader":{
				"name":"streamreader",
				"parameter":{
					"column":[
						{
							"type":"string",
							"value":"zhangsan"
						},
						{
							"type":"string",
							"value":"18"
						}
					],
					"sliceRecordCount":"10"
				}
			},
			"writer":{
				"name":"streamwriter",
				"parameter":{
					"encoding":"UTF-8",
					"print":true
				}
			}
		}
	],
	"setting":{
		"speed":{
			"channel":"1"
		}
	}
}

2021-12-24 10:33:32.946 [main] WARN  Engine - prioriy set to 0, because NumberFormatException, the value is: null
2021-12-24 10:33:32.950 [main] INFO  PerfTrace - PerfTrace traceId=job_-1, isEnable=false, priority=0
2021-12-24 10:33:32.950 [main] INFO  JobContainer - DataX jobContainer starts job.
2021-12-24 10:33:32.958 [main] INFO  JobContainer - Set jobId = 0
2021-12-24 10:33:32.998 [job-0] INFO  JobContainer - jobContainer starts to do prepare ...
2021-12-24 10:33:32.999 [job-0] INFO  JobContainer - DataX Reader.Job [streamreader] do prepare work .
2021-12-24 10:33:33.000 [job-0] INFO  JobContainer - DataX Writer.Job [streamwriter] do prepare work .
2021-12-24 10:33:33.000 [job-0] INFO  JobContainer - jobContainer starts to do split ...
2021-12-24 10:33:33.000 [job-0] INFO  JobContainer - Job set Channel-Number to 1 channels.
2021-12-24 10:33:33.001 [job-0] INFO  JobContainer - DataX Reader.Job [streamreader] splits to [1] tasks.
2021-12-24 10:33:33.002 [job-0] INFO  JobContainer - DataX Writer.Job [streamwriter] splits to [1] tasks.
2021-12-24 10:33:33.040 [job-0] INFO  JobContainer - jobContainer starts to do schedule ...
2021-12-24 10:33:33.052 [job-0] INFO  JobContainer - Scheduler starts [1] taskGroups.
2021-12-24 10:33:33.057 [job-0] INFO  JobContainer - Running by standalone Mode.
2021-12-24 10:33:33.084 [taskGroup-0] INFO  TaskGroupContainer - taskGroupId=[0] start [1] channels for [1] tasks.
2021-12-24 10:33:33.098 [taskGroup-0] INFO  Channel - Channel set byte_speed_limit to -1, No bps activated.
2021-12-24 10:33:33.098 [taskGroup-0] INFO  Channel - Channel set record_speed_limit to -1, No tps activated.
2021-12-24 10:33:33.132 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] taskId[0] attemptCount[1] is started
zhangsan	18
zhangsan	18
zhangsan	18
zhangsan	18
zhangsan	18
zhangsan	18
zhangsan	18
zhangsan	18
zhangsan	18
zhangsan	18
2021-12-24 10:33:33.243 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] taskId[0] is successed, used[119]ms
2021-12-24 10:33:33.243 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] completed it's tasks.
2021-12-24 10:33:43.107 [job-0] INFO  StandAloneJobContainerCommunicator - Total 10 records, 100 bytes | Speed 10B/s, 1 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 0.000s |  All Task WaitReaderTime 0.000s | Percentage 100.00%
2021-12-24 10:33:43.108 [job-0] INFO  AbstractScheduler - Scheduler accomplished all tasks.
2021-12-24 10:33:43.108 [job-0] INFO  JobContainer - DataX Writer.Job [streamwriter] do post work.
2021-12-24 10:33:43.109 [job-0] INFO  JobContainer - DataX Reader.Job [streamreader] do post work.
2021-12-24 10:33:43.109 [job-0] INFO  JobContainer - DataX jobId [0] completed successfully.
2021-12-24 10:33:43.110 [job-0] INFO  HookInvoker - No hook invoked, because base dir not exists or is a file: /root/datax/hook
2021-12-24 10:33:43.112 [job-0] INFO  JobContainer - 
	 [total cpu info] => 
		averageCpu                     | maxDeltaCpu                    | minDeltaCpu                    
		-1.00%                         | -1.00%                         | -1.00%
                        

	 [total gc info] => 
		 NAME                 | totalGCCount       | maxDeltaGCCount    | minDeltaGCCount    | totalGCTime        | maxDeltaGCTime     | minDeltaGCTime     
		 PS MarkSweep         | 0                  | 0                  | 0                  | 0.000s             | 0.000s             | 0.000s             
		 PS Scavenge          | 0                  | 0                  | 0                  | 0.000s             | 0.000s             | 0.000s             

2021-12-24 10:33:43.112 [job-0] INFO  JobContainer - PerfTrace not enable!
2021-12-24 10:33:43.113 [job-0] INFO  StandAloneJobContainerCommunicator - Total 10 records, 100 bytes | Speed 10B/s, 1 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 0.000s |  All Task WaitReaderTime 0.000s | Percentage 100.00%
2021-12-24 10:33:43.114 [job-0] INFO  JobContainer - 
任务启动时刻                    : 2021-12-24 10:33:32
任务结束时刻                    : 2021-12-24 10:33:43
任务总计耗时                    :                 10s
任务平均流量                    :               10B/s
记录写入速度                    :              1rec/s
读出记录总数                    :                  10
读写失败总数                    :                   0

[root@localhost datax]# 

```


#### 3.2 读取MySQL中的数据存放到HDFS/Mysql
```
-- 查看模板文件
bin/datax.py -r mysqlreader -w hdfswriter

-- 执行自定义的job
bin/datax.py ./job/stream2streamjob.json
```


- mysqlreader to hdfswriter模板参数解析(HDFS使用column的时候需要指定类型)
```
{
    "job": {
        "content": [
            {
                "reader": {
	                -- reader名
                    "name": "mysqlreader", 
                    "parameter": {
	                    -- 需要同步的列名集合,使用json数组描述自带信息,* 代表所有列
                        "column": [], 
                        "connection": [
                            {
	                            -- 对数据库的JDBC连接信息,使用JSON数组描述,支持多个连接地址
                                "jdbcUrl": [], 
                                -- 需要同步的表，支持多个
                                "table": [],
                                -- 可选配置(优先级最高)自定义SQL,配置它后,mysqlreader直接忽略table、column、where
                                【"queryUrl":[]】
                            }
                        ], 
                        -- 数据库用户名对应的密码
                        "password": "", 
                        -- 数据库用户名
                        "username": "", 
                        -- 可选配置：筛选条件
                       【 "where": ""】,
                        -- 可选配置：数据分片字段，一般是主键，仅支持整型,这样均分会均匀一点
                       【 "splitPk":""】
                    }
                }, 
                "writer": {
	                -- writer名
                    "name": "hdfswriter", 
                    "parameter": {
	                    -- 写入数据的字段，其中name指定字段名，type指定类型
                        "column": [], 
                        -- hdfs文件压缩类型，默认不填写意味着没有压缩。
                        "compress": "", 
                        -- hdfs文件系统namenode节点地址，格式:hdfs://ip:端口
                        "defaultFS": "", 
                        -- 字段分隔符
                        "fieldDelimiter": "", 
                        -- 写入文件名
                        "fileName": "", 
                        -- 文件的类型，目前只支持用户配置为"text"或"orc"
                        "fileType": "", 
                        -- 存储到Hadoop hdfs文件系统的路径信息
                        "path": "", 
                        -- hdfswriter写入前数据清理处理模式:追加还是覆盖
                        (1)append:写入前不做任何处理,DataX hdfswriter直接使用filename写入,
并保证文件名不冲突。
						(2)nonConflict:如果目录下有fileName前缀的文件,直接报错。
                        "writeMode": ""
                    }
                }
            }
        ], 
        "setting": {
            "speed": {
                "channel": ""
            }
        }
    }
}

```


- mysqlreader to mysqlwriter模板参数解析
```
{
    "job": {
        "content": [
            {
                "reader": {
                    "name": "mysqlreader", 
                    "parameter": {
                        "column": [], 
                        "connection": [
                            {
                                "jdbcUrl": [], 
                                "table": []
                            }
                        ], 
                        "password": "", 
                        "username": "", 
                        "where": ""
                    }
                }, 
                "writer": {
                    "name": "mysqlwriter", 
                    "parameter": {
                        "column": [], 
                        "connection": [
                            {
                                "jdbcUrl": "", 
                                "table": []
                            }
                        ], 
                        "password": "", 
                        "preSql": [], 
                        "session": [], 
                        "username": "", 
                        -- 特殊：重要,写入目标数据表的模式，可选项： replace（替换），update（更新），insert(插入)
                        "writeMode": ""
                    }
                }
            }
        ], 
        "setting": {
            "speed": {
                "channel": ""
            }
        }
    }
}

```


- mysql to mysql 的启动保存日志
```
[root@localhost datax]# bin/datax.py ./job/mysql2mysqljob.json 

DataX (DATAX-OPENSOURCE-3.0), From Alibaba !
Copyright (C) 2010-2017, Alibaba Group. All Rights Reserved.


2021-12-24 11:49:09.617 [main] INFO  VMInfo - VMInfo# operatingSystem class => sun.management.OperatingSystemImpl
2021-12-24 11:49:09.636 [main] INFO  Engine - the machine info  => 

	osInfo:	Oracle Corporation 1.8 25.291-b10
	jvmInfo:	Linux amd64 3.10.0-1160.31.1.el7.x86_64
	cpu num:	2

	totalPhysicalMemory:	-0.00G
	freePhysicalMemory:	-0.00G
	maxFileDescriptorCount:	-1
	currentOpenFileDescriptorCount:	-1

	GC Names	[PS MarkSweep, PS Scavenge]

	MEMORY_NAME                    | allocation_size                | init_size                      
	PS Eden Space                  | 256.00MB                       | 256.00MB                       
	Code Cache                     | 240.00MB                       | 2.44MB                         
	Compressed Class Space         | 1,024.00MB                     | 0.00MB                         
	PS Survivor Space              | 42.50MB                        | 42.50MB                        
	PS Old Gen                     | 683.00MB                       | 683.00MB                       
	Metaspace                      | -0.00MB                        | 0.00MB                         


2021-12-24 11:49:09.691 [main] INFO  Engine - 
{
	"content":[
		{
			"reader":{
				"name":"mysqlreader",
				"parameter":{
					"column":[
						"id",
						"name"
					],
					"connection":[
						{
							"jdbcUrl":[
								"jdbc:mysql://192.168.0.100:3306/test?serverTimezone=UTC&characterEncoding=utf-8&allowMultiQueries=true"
							],
							"table":[
								"student"
							]
						}
					],
					"password":"******",
					"username":"root",
					"where":""
				}
			},
			"writer":{
				"name":"mysqlwriter",
				"parameter":{
					"column":[
						"id",
						"name"
					],
					"connection":[
						{
							"jdbcUrl":"jdbc:mysql://192.168.0.100:3306/mybatis?serverTimezone=UTC&characterEncoding=utf-8&allowMultiQueries=true",
							"table":[
								"student"
							]
						}
					],
					"password":"******",
					"preSql":[],
					"session":[],
					"username":"root",
					"writeMode":"insert"
				}
			}
		}
	],
	"setting":{
		"speed":{
			"channel":"1"
		}
	}
}

2021-12-24 11:49:09.732 [main] WARN  Engine - prioriy set to 0, because NumberFormatException, the value is: null
2021-12-24 11:49:09.736 [main] INFO  PerfTrace - PerfTrace traceId=job_-1, isEnable=false, priority=0
2021-12-24 11:49:09.737 [main] INFO  JobContainer - DataX jobContainer starts job.
2021-12-24 11:49:09.745 [main] INFO  JobContainer - Set jobId = 0
2021-12-24 11:49:10.456 [job-0] INFO  OriginalConfPretreatmentUtil - Available jdbcUrl:jdbc:mysql://192.168.0.100:3306/test?serverTimezone=UTC&characterEncoding=utf-8&allowMultiQueries=true&yearIsDateType=false&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&rewriteBatchedStatements=true.
2021-12-24 11:49:10.482 [job-0] INFO  OriginalConfPretreatmentUtil - table:[student] has columns:[id,name].
2021-12-24 11:49:10.897 [job-0] INFO  OriginalConfPretreatmentUtil - table:[student] all columns:[
id,name
].
2021-12-24 11:49:10.928 [job-0] INFO  OriginalConfPretreatmentUtil - Write data [
insert INTO %s (id,name) VALUES(?,?)
], which jdbcUrl like:[jdbc:mysql://192.168.0.100:3306/mybatis?serverTimezone=UTC&characterEncoding=utf-8&allowMultiQueries=true&yearIsDateType=false&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&rewriteBatchedStatements=true]
2021-12-24 11:49:10.929 [job-0] INFO  JobContainer - jobContainer starts to do prepare ...
2021-12-24 11:49:10.929 [job-0] INFO  JobContainer - DataX Reader.Job [mysqlreader] do prepare work .
2021-12-24 11:49:10.930 [job-0] INFO  JobContainer - DataX Writer.Job [mysqlwriter] do prepare work .
2021-12-24 11:49:10.933 [job-0] INFO  JobContainer - jobContainer starts to do split ...
2021-12-24 11:49:10.933 [job-0] INFO  JobContainer - Job set Channel-Number to 1 channels.
2021-12-24 11:49:10.943 [job-0] INFO  JobContainer - DataX Reader.Job [mysqlreader] splits to [1] tasks.
2021-12-24 11:49:10.945 [job-0] INFO  JobContainer - DataX Writer.Job [mysqlwriter] splits to [1] tasks.
2021-12-24 11:49:10.988 [job-0] INFO  JobContainer - jobContainer starts to do schedule ...
2021-12-24 11:49:10.994 [job-0] INFO  JobContainer - Scheduler starts [1] taskGroups.
2021-12-24 11:49:11.001 [job-0] INFO  JobContainer - Running by standalone Mode.
2021-12-24 11:49:11.026 [taskGroup-0] INFO  TaskGroupContainer - taskGroupId=[0] start [1] channels for [1] tasks.
2021-12-24 11:49:11.037 [taskGroup-0] INFO  Channel - Channel set byte_speed_limit to -1, No bps activated.
2021-12-24 11:49:11.037 [taskGroup-0] INFO  Channel - Channel set record_speed_limit to -1, No tps activated.
2021-12-24 11:49:11.064 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] taskId[0] attemptCount[1] is started
2021-12-24 11:49:11.070 [0-0-0-reader] INFO  CommonRdbmsReader$Task - Begin to read record by Sql: [select id,name from student 
] jdbcUrl:[jdbc:mysql://192.168.0.100:3306/test?serverTimezone=UTC&characterEncoding=utf-8&allowMultiQueries=true&yearIsDateType=false&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&rewriteBatchedStatements=true].
2021-12-24 11:49:11.122 [0-0-0-reader] INFO  CommonRdbmsReader$Task - Finished read record by Sql: [select id,name from student 
] jdbcUrl:[jdbc:mysql://192.168.0.100:3306/test?serverTimezone=UTC&characterEncoding=utf-8&allowMultiQueries=true&yearIsDateType=false&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&rewriteBatchedStatements=true].
2021-12-24 11:49:11.570 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] taskId[0] is successed, used[509]ms
2021-12-24 11:49:11.571 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] completed it's tasks.
2021-12-24 11:49:21.063 [job-0] INFO  StandAloneJobContainerCommunicator - Total 3 records, 21 bytes | Speed 2B/s, 0 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 0.000s |  All Task WaitReaderTime 0.000s | Percentage 100.00%
2021-12-24 11:49:21.064 [job-0] INFO  AbstractScheduler - Scheduler accomplished all tasks.
2021-12-24 11:49:21.065 [job-0] INFO  JobContainer - DataX Writer.Job [mysqlwriter] do post work.
2021-12-24 11:49:21.066 [job-0] INFO  JobContainer - DataX Reader.Job [mysqlreader] do post work.
2021-12-24 11:49:21.067 [job-0] INFO  JobContainer - DataX jobId [0] completed successfully.
2021-12-24 11:49:21.069 [job-0] INFO  HookInvoker - No hook invoked, because base dir not exists or is a file: /root/datax/hook
2021-12-24 11:49:21.072 [job-0] INFO  JobContainer - 
	 [total cpu info] => 
		averageCpu                     | maxDeltaCpu                    | minDeltaCpu                    
		-1.00%                         | -1.00%                         | -1.00%
                        

	 [total gc info] => 
		 NAME                 | totalGCCount       | maxDeltaGCCount    | minDeltaGCCount    | totalGCTime        | maxDeltaGCTime     | minDeltaGCTime     
		 PS MarkSweep         | 0                  | 0                  | 0                  | 0.000s             | 0.000s             | 0.000s             
		 PS Scavenge          | 0                  | 0                  | 0                  | 0.000s             | 0.000s             | 0.000s             

2021-12-24 11:49:21.089 [job-0] INFO  JobContainer - PerfTrace not enable!
2021-12-24 11:49:21.091 [job-0] INFO  StandAloneJobContainerCommunicator - Total 3 records, 21 bytes | Speed 2B/s, 0 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 0.000s |  All Task WaitReaderTime 0.000s | Percentage 100.00%
2021-12-24 11:49:21.093 [job-0] INFO  JobContainer - 
任务启动时刻                    : 2021-12-24 11:49:09
任务结束时刻                    : 2021-12-24 11:49:21
任务总计耗时                    :                 11s
任务平均流量                    :                2B/s
记录写入速度                    :              0rec/s
读出记录总数                    :                   3
读写失败总数                    :                   0

[root@localhost datax]# 

```

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224115205836-1284380210.png)


[Mysqlwriter如何配置具体参考文档](https://github.com/alibaba/DataX/blob/master/mysqlwriter/doc/mysqlwriter.md)



- 文件写入
全部成功---->修改文件名、路径
如果个别失败---->job失败---->则除临时路径


- 导入Hive
hdfswriter--->load data 进hive表--->直接写入hive表对应的路径



#### 关于HA支持(下面的配置写到parameter即可)
```
"hadoopConfig":{
	"dfs.nameservices": "ns",
	"dfs.ha.namenodes.ns" : "nn1,nn2",
	"dfs.namenode.rpc-address.ns.nn1": "主机名:端口",
	"dfs.namenode.rpc-address.ns.nn2":"主机名:端口",
	"dfs.client.failover.proxy.provider.ns":
		"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvide
r"}

```



### 四、Oracle数据库

#### 4.1 Oracle 数据库简介
Oracle Database，又名Oracle RDBMS，或简称Oracle。是甲骨文公司的一款关系数据库管理系统。它是在数据库领域一直处于领先地位的产品。可以说Oracle数据库系统是目世界上流行的关系数据库管理系统，系统可移植性好、使用方便、功能强，适用于各类大中、小、微机环境。它是一种高效率、可靠性好的、适应高吞吐量的数据库解决方案。


#### 4.2 安装前准备
##### 4.2.1 安装依赖
```
yum install bc binutils compat-libcapl compat-libstdc++33 elfutils-libelf
elfutils-libelf-devel fontconfig-devel glibc glibc-devel ksh libaio libaio-devel libX11libXau libXi libXtst libXrender libXrender-devel libgcc libstdc++ libstdc++-devel libxcb make smartmontools sysstat kmod*
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224144853642-1713315120.png)


##### 4.2.2 配置用户组
Oracle安装文件不允许通过root用户启动，需要为oracle配置一个专门的用户
1)创建sql 用户组
```
groupadd sql
```

2）创建oracle用户并放入sql组中
```
useradd oracle -g sql
```

3）修改oracle用户登录密码，输入密码后即可使用oracle用户登录系统
```
passwd oracle
```

4）修改所属用户和组
```
chown -R oracle:sql /opt/module/解压Oracle文件的路径/
chgrp -R sql /opt/module/解压Oracle文件的路径/
```

##### 4.2.3 上传安装包并解压
**19C 要求Centos7才可以安装，19C需要把软件包直接解压到ORACLE_HOME的目录下**
```
unzip Oracle_Database_12c_Release2_linuxx64.zip -d /opt/module/oracle

mkdir -p /home/oracle/app/oracle/product/19.3.0/dbhome_1
unzip LINUX.X64_193000_db_home.zip -d
/home/oracle/app/oracle/product/19.3.0/dbhome_1

```

- 修改所属用户和组
```
chown -R oracle:sql /home/oracle/app/oracle/product/19.3.0/dbhome_1
```

##### 4.2.4 修改配置文件sysctl.conf
```
vim /etc/sysctl.conf

删除里面的内容,添加下面的内容

net.ipv4.ip_local_port_range = 9000 65500
fs.file-max = 6815744
kernel.shmall =10523004
kernel.shmmax =6465333657
kernel.shmmni = 4096
kernel.sem= 250 32000 100 128
net.core.rmem_default=262144
net.core.wmem_default=262144
net.core.rmem_max=4194304
net.core.wmem_max=1048576
fs.aio-max-nr = 1048576

```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224145940131-632492682.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224150334084-673463810.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224150304001-1364719450.png)


##### 4.2.5 修改配置文件limits.conf
```
vim /etc/security/limits.conf


在文件末尾追加(oracle 是用户名)

oracle soft nproc 2047
oracle hard nproc 16384
oracle soft nofile 1024
oracle hard nofile 65536

```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224150629972-1821052922.png)

>重启机器生效


#### 4.3 安装Oracle数据库
- 设置环境变量 
```
vim ~/.bash_profile

文件末尾添加下面的内容
#ORACLE_HOME
export ORACLE_BASE=/home/oracle/app/oracle
export ORACLE_HOME=/home/oracle/app/oracle/product/19.3.0/dbhome_1
export PATH=$PATH:$ORACLE_HOME/bin
export ORACLE_SID=orcl
export NLS_LANG=AMERICAN_AMERICA.ZHS16GBK

更新资源配置文件
source ~/.bash_profile
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224151847728-1036085779.png)


- 进入虚拟机图形化操作界面
在dbhome_1  文件夹下面有个 runInstaller执行文件  .runInstaller

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224152414803-1689981680.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224152614766-188475119.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224152629616-2087355405.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224152647143-248038020.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224152711544-54719262.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224152731294-2083618616.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224152755812-1075113553.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224152810646-1140306401.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224152822976-484721982.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224152846326-750970483.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224152859773-44600713.png)

>中间会出现一次有一个命令需要以root的用户执行,就是这么来的


>验证是否安装成功，输入net 看到netca表示安装好了


#### 4.4 设置Oracle监听
在bash命令行输入  `netca` 即可弹出程序

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153437211-2053497156.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153501813-1576338956.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153521948-649609134.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153543096-109985427.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153554638-901497146.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153607165-1478845894.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153639347-355954689.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153628492-611729719.png)


#### 4.5 创建Oracle数据库
在bash命令行输入  `dbca` 即可弹出程序

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153803020-1609489758.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153829609-1063597216.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153850851-1764222545.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153908783-1537652158.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153928292-687598194.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153948199-963424544.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224153957669-626118473.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224154034545-1433748725.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224154043593-1071654022.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224154105529-1098809371.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224154129248-1748868145.png)
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224154140653-1336792697.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224154150104-1224373931.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224154208998-203543127.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224154218004-1322413579.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224154225297-1037881895.png)


#### 4.6 简单使用
##### 4.6.1开启，关闭监听服务
```
开启服务：lsnrctl start
关闭服务：lsnrctl stop
```

##### 4.6.2 进入命令行
```
sqlplus
```


##### 4.6.3 创建用户并授权
```
切换用户记得带 -
su - oracle

sqlplus

SQL>create user legend identified by 000000;

SQL>grant create session,create table,create view,create sequence,unlimited tablespace to legend;
```

##### 4.6.4 进入legend账号,创建表
```
create table student(id int, name varchar2(25));

insert into student values (1, 'zhangsan');

select * from student;

-- 事务需要提交不然其他会话看不到
commit;

```

>注意:安装完成后重启机器可能出现 ORACLE not available错误，解决方法如下:

```
[oracle@centos7~]$ sqlplus / as sysdba

进入Oracle终端
SQL>startup
SQL>conn legend
```

#### 4.7 Oracle 与 MySQL的SQL区别
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224161308917-1790568439.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224161711920-1855067170.png)


#### 4.8 DataX案例

##### 4.8.1 从Oracle中读取数据存到MySQL
```
-- 查看json模板文件
bin/datax.py -r oraclereader -w mysqlwriter
```

- 编写json文件  vim ./job/oracle2mysql.json
```
{
    "job": {
        "content": [
            {
                "reader": {
                    "name": "oraclereader",
                    "parameter": {
                    	-- 尽量使用确定的字段名
                        "column": [
                            "*"
                        ],
                        "connection": [
                            {
                                "jdbcUrl": [
	                                -- oral是实例名SID
                                    "jdbc:oracle:thin:@192.168.56.10:1521:orcl"
                                ],
                                "table": [
                                    "student"
                                ]
                            }
                        ],
                        "password": "000000",
                        "username": "jason"
                    }
                },
                "writer": {
                    "name": "mysqlwriter",
                    "parameter": {
	                    -- 尽量使用确定的字段名
                        "column": [
                            "*"
                        ],
                        "connection": [
                            {
                                "jdbcUrl": "jdbc:mysql://192.168.0.100:3306/oracle",
                                "table": [
                                    "student"
                                ]
                            }
                        ],
                        "password": "123456",
                        "username": "root",
                        "writeMode": "insert"
                    }
                }
            }
        ],
        "setting": {
            "speed": {
                "channel": "1"
            }
        }
    }
}
```



##### 4.8.2 从Oracle中读取数据存到HDFS
```
-- 查看模板文件
bin/datax.py -r oraclereader -w hdfswriter

-- 执行自定义的job
bin/datax.py ./job/oracle2hdfs.json
```

- 编写json文件 vim ./job/oracle2hdfs.json
```
{
    "job": {
        "content": [
            {
                "reader": {
                    "name": "oraclereader",
                    "parameter": {
                        "column": [
                            "*"
                        ],
                        "connection": [
                            {
                                "jdbcUrl": [
                                    "jdbc:oracle:thin:@192.168.56.10:1521:orcl"
                                ],
                                "table": [
                                    "student"
                                ]
                            }
                        ],
                        "password": "123456",
                        "username": "jason"
                    }
                },
                "writer": {
                    "name": "hdfswriter",
                    "parameter": {
                        "column": [
                            {
                                "name": "id",
                                "type": "int"
                            },
                            {
                                "name": "name",
                                "type": "string"
                            }
                        ],
                        "defaultFS": "hdfs://192.168.56.10:9000",
                        "fieldDelimiter": "\t",
                        "fileName": "oracle.txt",
                        "fileType": "text",
                        "path": "/",
                        "writeMode": "append"
                    }
                }
            }
        ],
        "setting": {
            "speed": {
                "channel": "1"
            }
        }
    }
}
```


### 五、MongoDB
#### 5.1 什么是MongoDB
MongoDB是由C++语言编写的，是一个基于分布式文件存储的开源数据库系统。MongoDB旨在为 WEB应用提供可扩展的高性能数据存储解决方案。MongoDB将数据存储为一个文档，数据结构由键值(key=>value)对组成。MongoDB文档类似于JSON对象。字段值可以包含其他文档，数组及文档数组。

文档数据格式（JSON一个半结构化数据）：
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224164611435-177394128.png)


#### 5.2 MongoDB的优缺点
##### 优点:
- 1.MongoDB是一个面向文档存储的数据库，操作起来比较简单和容易;
- 2.内置GridFS，支持大容量的存储;
- 3.可以在MongoDB记录中设置任何属性的索引;
- 4.MongoDB支持各种编程语言:RUBY，PYTHON，JAVA，CH+，PHP，C#等多种语言;
- 5.安装简单;
- 6.复制（复制集）和支持自动故障恢复;I7.MapReduce支持复杂聚合。


##### 缺点
- 1.不支持事务;
- 2.占用空间过大;
- 3.不能进行表关联;
- 4.复杂聚合操作通过MapReduce创建，速度慢;
- 5.MongoDB在你删除记录后不会在文件系统回收空间。除非你删掉数据库。


#### 5.3 基础概念解析
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224171529966-1579742466.png)


#### 5.4 安装
 