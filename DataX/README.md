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
- 安装地址
[MongoDB下载地址](https://www.mongodb.com/try/download/community)
 
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225124642508-351543349.png)

- 安装
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225132025821-516152264.png)

#### 5.5 基础概念详解

##### 5.5.1 数据库
- admin:从权限的角度来看，这是"root"数据库。要是将一个用户添加到这个数据库，这个用户自动继承所有数据库的权限。一些特定的服务器端命令也只能从这个数据库运行，比如列出所有的数据库或者关闭服务器。
- local:这个数据永远不会被复制，可以用来存储限于本地单台服务器的任意集合
- config:当Mongo用于分片设置时，config 数据库在内部使用，用于保存分片的相关信息。


2）显示当前使用的数据库
```
>db
test
```
3）切换数据库
```
> use local
switched to db locale 

>db
local
```

##### 5.5.2 集合
集合就是MongoDB 文档组，类似于MySQL中的table。
集合存在于数据库中，集合没有固定的结构，这意味着你在对集合可以插入不同格式和类型的数据，但通常情况下我们插入集合的数据都会有一定的关联性。

MongoDB中使用createCollection()方法来创建集合。下面来看看如何创建集合:
```
db.createCollection(name, options)

参数说明：
	name：要创建的集合的名称
	options:可选参数，指定有关内存大小及索引的选项，有以下参数:

```


```
//查看所有数据库
show dbs;

//查看当前数据库
db;

//创建表
db.createCollection("hello_world");

//查看表
show collections;

//插入数据
db.hello_world.insert({"name":"123", "url":"https://wwww.baidu.com"});
db.hello_world.insert({"name":"1", "url":"https://wwww.taobao.com"});

//查看所有数据 & 查看某条数据
db.hello_world.find()
db.hello_world.find({"name":"1"})
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225144139539-888461305.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225144402153-125318287.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225144613976-1642449697.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225144653128-299011384.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225144839252-1289407665.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225145217902-428521126.png)

**说明：**
- Objectld 类似唯一主键，可以很快的去生成和排序，包含 12 bytes，由24个16进制数字组成的字符串（每个字节可以存储两个16进制数字）,含义是：
前4个字节表示创建unix 时间戳
接下来的3个字节是机器标识码
紧接的两个字节由进程id 组成PID
最后三个字节是随机数



```
//创建一个固定集合mycol
db.createCollection("mycol",{ capped : true,autoIndexId : true,size : 6142800, max:1000});

show tables;

//自动创建集合
db.mycol2.insert({"name":"wangwu"})

db.mycol2.find()
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225145744536-1496876885.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225145809991-760390902.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225145956340-1808663793.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225150008678-331620390.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225150033306-594079235.png)

>在MongoDB中，你不需要创建集合。当你插入一些文档时，MongoDB会自动创建集合。


```
d
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225150453893-2114186085.png)


##### 5.5.3 文档
文档是一组键值(key-value)对组成。MongoDB的文档不需要设置相同的字段，并且相同的字段不需要相同的数据类型，这与关系型数据库有很大的区别，也是MongoDB非常突出的特点。
一个简单的例子:
```
{"name":"hello"}
```

**注意:**
- 1、文档中的键/值对是有序的。
- 2、MongoDB区分类型和大小写。
- 3、MongoDB的文档不能有重复的键。
- 4、文档的键是字符串。除了少数例外情况，键可以使用任意UTF-8字符。

#### 5.6 DataX 导入导出案例
##### 5.6.1 读取MongoDB数据导入到MySQL
- 查看MongoDB端口
```
pgrep -f mongod
等价于
ps -ef | grep mongod | grep -v grep awk '{print $2}'


netstat -anp | grep pid

-- 不指定本机,
bin/mongod -bind_ip 0.0.0.0
```


- 查看json模板   bin/datax.py -r mongodbreader -w mysqlwriter
```
[root@localhost datax]# bin/datax.py ./job/mongodb2mysqljob.json 

DataX (DATAX-OPENSOURCE-3.0), From Alibaba !
Copyright (C) 2010-2017, Alibaba Group. All Rights Reserved.


2021-12-25 15:50:22.696 [main] INFO  VMInfo - VMInfo# operatingSystem class => sun.management.OperatingSystemImpl
2021-12-25 15:50:22.710 [main] INFO  Engine - the machine info  => 

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


2021-12-25 15:50:22.746 [main] INFO  Engine - 
{
	"content":[
		{
			"reader":{
				"name":"mongodbreader",
				"parameter":{
					"address":[
						"192.168.1.37:27017"
					],
					"collectionName":"mongo2mysql",
					"column":[
						{
							"name":"name",
							"type":"string"
						},
						{
							"name":"url",
							"type":"string"
						}
					],
					"dbName":"test"
				}
			},
			"writer":{
				"name":"mysqlwriter",
				"parameter":{
					"column":[
						"name",
						"url"
					],
					"connection":[
						{
							"jdbcUrl":"jdbc:mysql://192.168.1.37:3306/test",
							"table":[
								"mongo2mysql"
							]
						}
					],
					"password":"******",
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

2021-12-25 15:50:22.784 [main] WARN  Engine - prioriy set to 0, because NumberFormatException, the value is: null
2021-12-25 15:50:22.789 [main] INFO  PerfTrace - PerfTrace traceId=job_-1, isEnable=false, priority=0
2021-12-25 15:50:22.789 [main] INFO  JobContainer - DataX jobContainer starts job.
2021-12-25 15:50:22.798 [main] INFO  JobContainer - Set jobId = 0
2021-12-25 15:50:23.057 [job-0] INFO  cluster - Cluster created with settings {hosts=[192.168.1.37:27017], mode=MULTIPLE, requiredClusterType=UNKNOWN, serverSelectionTimeout='30000 ms', maxWaitQueueSize=500}
2021-12-25 15:50:23.058 [job-0] INFO  cluster - Adding discovered server 192.168.1.37:27017 to client view of cluster
2021-12-25 15:50:23.259 [cluster-ClusterId{value='61c6cd3fba4a5b44e8c8b90c', description='null'}-192.168.1.37:27017] INFO  connection - Opened connection [connectionId{localValue:1, serverValue:9}] to 192.168.1.37:27017
2021-12-25 15:50:23.262 [cluster-ClusterId{value='61c6cd3fba4a5b44e8c8b90c', description='null'}-192.168.1.37:27017] INFO  cluster - Monitor thread successfully connected to server with description ServerDescription{address=192.168.1.37:27017, type=STANDALONE, state=CONNECTED, ok=true, version=ServerVersion{versionList=[3, 6, 11]}, minWireVersion=0, maxWireVersion=6, maxDocumentSize=16777216, roundTripTimeNanos=1386099}
2021-12-25 15:50:23.263 [cluster-ClusterId{value='61c6cd3fba4a5b44e8c8b90c', description='null'}-192.168.1.37:27017] INFO  cluster - Discovered cluster type of STANDALONE
2021-12-25 15:50:23.677 [job-0] INFO  OriginalConfPretreatmentUtil - table:[mongo2mysql] all columns:[
id,name,url
].
2021-12-25 15:50:23.700 [job-0] INFO  OriginalConfPretreatmentUtil - Write data [
insert INTO %s (name,url) VALUES(?,?)
], which jdbcUrl like:[jdbc:mysql://192.168.1.37:3306/test?yearIsDateType=false&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&rewriteBatchedStatements=true]
2021-12-25 15:50:23.701 [job-0] INFO  JobContainer - jobContainer starts to do prepare ...
2021-12-25 15:50:23.702 [job-0] INFO  JobContainer - DataX Reader.Job [mongodbreader] do prepare work .
2021-12-25 15:50:23.703 [job-0] INFO  JobContainer - DataX Writer.Job [mysqlwriter] do prepare work .
2021-12-25 15:50:23.705 [job-0] INFO  JobContainer - jobContainer starts to do split ...
2021-12-25 15:50:23.706 [job-0] INFO  JobContainer - Job set Channel-Number to 1 channels.
2021-12-25 15:50:23.747 [job-0] INFO  connection - Opened connection [connectionId{localValue:2, serverValue:10}] to 192.168.1.37:27017
2021-12-25 15:50:23.767 [job-0] INFO  JobContainer - DataX Reader.Job [mongodbreader] splits to [1] tasks.
2021-12-25 15:50:23.768 [job-0] INFO  JobContainer - DataX Writer.Job [mysqlwriter] splits to [1] tasks.
2021-12-25 15:50:23.812 [job-0] INFO  JobContainer - jobContainer starts to do schedule ...
2021-12-25 15:50:23.823 [job-0] INFO  JobContainer - Scheduler starts [1] taskGroups.
2021-12-25 15:50:23.828 [job-0] INFO  JobContainer - Running by standalone Mode.
2021-12-25 15:50:23.852 [taskGroup-0] INFO  TaskGroupContainer - taskGroupId=[0] start [1] channels for [1] tasks.
2021-12-25 15:50:23.860 [taskGroup-0] INFO  Channel - Channel set byte_speed_limit to -1, No bps activated.
2021-12-25 15:50:23.861 [taskGroup-0] INFO  Channel - Channel set record_speed_limit to -1, No tps activated.
2021-12-25 15:50:23.900 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] taskId[0] attemptCount[1] is started
2021-12-25 15:50:23.903 [0-0-0-reader] INFO  cluster - Cluster created with settings {hosts=[192.168.1.37:27017], mode=MULTIPLE, requiredClusterType=UNKNOWN, serverSelectionTimeout='30000 ms', maxWaitQueueSize=500}
2021-12-25 15:50:23.904 [0-0-0-reader] INFO  cluster - Adding discovered server 192.168.1.37:27017 to client view of cluster
2021-12-25 15:50:23.933 [cluster-ClusterId{value='61c6cd3fba4a5b44e8c8b90d', description='null'}-192.168.1.37:27017] INFO  connection - Opened connection [connectionId{localValue:3, serverValue:11}] to 192.168.1.37:27017
2021-12-25 15:50:23.943 [cluster-ClusterId{value='61c6cd3fba4a5b44e8c8b90d', description='null'}-192.168.1.37:27017] INFO  cluster - Monitor thread successfully connected to server with description ServerDescription{address=192.168.1.37:27017, type=STANDALONE, state=CONNECTED, ok=true, version=ServerVersion{versionList=[3, 6, 11]}, minWireVersion=0, maxWireVersion=6, maxDocumentSize=16777216, roundTripTimeNanos=1900511}
2021-12-25 15:50:23.943 [cluster-ClusterId{value='61c6cd3fba4a5b44e8c8b90d', description='null'}-192.168.1.37:27017] INFO  cluster - Discovered cluster type of STANDALONE
2021-12-25 15:50:23.977 [0-0-0-reader] INFO  connection - Opened connection [connectionId{localValue:4, serverValue:12}] to 192.168.1.37:27017
2021-12-25 15:50:24.212 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] taskId[0] is successed, used[327]ms
2021-12-25 15:50:24.213 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] completed it's tasks.
2021-12-25 15:50:33.911 [job-0] INFO  StandAloneJobContainerCommunicator - Total 4 records, 71 bytes | Speed 7B/s, 0 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 0.000s |  All Task WaitReaderTime 0.000s | Percentage 100.00%
2021-12-25 15:50:33.912 [job-0] INFO  AbstractScheduler - Scheduler accomplished all tasks.
2021-12-25 15:50:33.912 [job-0] INFO  JobContainer - DataX Writer.Job [mysqlwriter] do post work.
2021-12-25 15:50:33.913 [job-0] INFO  JobContainer - DataX Reader.Job [mongodbreader] do post work.
2021-12-25 15:50:33.913 [job-0] INFO  JobContainer - DataX jobId [0] completed successfully.
2021-12-25 15:50:33.914 [job-0] INFO  HookInvoker - No hook invoked, because base dir not exists or is a file: /root/datax/hook
2021-12-25 15:50:33.916 [job-0] INFO  JobContainer - 
	 [total cpu info] => 
		averageCpu                     | maxDeltaCpu                    | minDeltaCpu                    
		-1.00%                         | -1.00%                         | -1.00%
                        

	 [total gc info] => 
		 NAME                 | totalGCCount       | maxDeltaGCCount    | minDeltaGCCount    | totalGCTime        | maxDeltaGCTime     | minDeltaGCTime     
		 PS MarkSweep         | 0                  | 0                  | 0                  | 0.000s             | 0.000s             | 0.000s             
		 PS Scavenge          | 0                  | 0                  | 0                  | 0.000s             | 0.000s             | 0.000s             

2021-12-25 15:50:33.917 [job-0] INFO  JobContainer - PerfTrace not enable!
2021-12-25 15:50:33.918 [job-0] INFO  StandAloneJobContainerCommunicator - Total 4 records, 71 bytes | Speed 7B/s, 0 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 0.000s |  All Task WaitReaderTime 0.000s | Percentage 100.00%
2021-12-25 15:50:33.919 [job-0] INFO  JobContainer - 
任务启动时刻                    : 2021-12-25 15:50:22
任务结束时刻                    : 2021-12-25 15:50:33
任务总计耗时                    :                 11s
任务平均流量                    :                7B/s
记录写入速度                    :              0rec/s
读出记录总数                    :                   4
读写失败总数                    :                   0

[root@localhost datax]# 
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225155409231-726153943.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225155516673-940615702.png)


### 六、SQL Server
#### 6.1 什么是SQL Server
美国Microsoft公司推出的一种关系型数据库系统。SQL Server是一个可扩展的、高性能的、为分布式客户机/服务器计算所设计的数据库管理系统，实现了与WindowsNT 的有机结合，提供了基于事务的企业级信息管理系统方案。SQL Server的基本语法和 MySQL基本相同。
(1）高性能设计，可充分利用WindowsNT的优势。
(2）系统管理先进，支持 Windows图形化管理工具，支持本地和远程的系统管理和配
置。-
(3）强壮的事务处理功能，采用各种方法保证数据的完整性。
(4)支持对称多处理器结构、存储过程、ODBC，并具有自主的SQL语言。SQLServer以其内置的数据复制功能、强大的管理工具、与Internet 的紧密集成和开放的系统结构为广大的用户、开发人员和系统集成商提供了一个出众的数据库平台。


#### 6.2 安装
##### 6.2.1 安装要求
系统要求:
1、centos或redhat7.0以上系统
2、内存2G以上-
说明:
linux下安装sqlserver数据库有2种办法:
使用rpm安装包安装
rpm安装包地址: https:/packages.microsoft.com/rhel/7/mssql-server-2017/安装时缺少什么依赖，就使用yum进行安装补齐
使用yum镜像安装


##### 6.2.2 安装步骤
1）下载 Microsoft sQL Server 2017 Red Hat存储库配置文件
```
sudo curl -o /etc/yum.repos.d/mssql-server.repo https://packages.microsoft.com/config/rhel/7/mssql-server-2017.repo
```

2）执行安装
```
yum install -y mssql-serverl
```

3）完毕之后运行做相关配置
```
sudo /opt/mssql/bin/mssql-conf setup
```

##### 6.2.3 安装配置
- 1）执行配置命令
```
sudo /opt/mssql/bin/mssql-conf setup  
```

- 2）选择安装的版本
1-8   2
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225160512837-780021021.png)

- 3）选择语言
10  中文简体

- 4）配置系统管理员密码

- 5）完成


##### 6.2.4 安装命令行工具
```
1）下载存储库配置文件
sudo curl -o /etc/yum.repos.d/msprod.repo https://packages.microsoft.com/config/rhel/7/prod.repo

2）执行安装
sudo yum remove mssql-tools unixODBC-utf16-devel
sudo yum install mssql-tools unixODBC-devel

3）配置环境变量
sudo vim /etc/profile.d/my_env.sh

#添加环境变量
export PATH="$PATH:/opt/mssql-tools/bin'
source /etc/profile.d/my_env.sh'

进入命令行工具
sqlcmd -S localhost -U SA -P 密码 #用命令行连接

```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211225161004766-2109661401.png)


#### 6.3简单使用

##### 6.3.1 启停命令
```
#启动
systemctl jstart mssql-server 

#重启
systemctl restart mssql-server

#停止
systemctl stop mssql-server

#查看状态
systemctl status mssql-server

#具体配置路径
/opt/mssql/bin/mssql-conf

```

##### 6.3.2 创建数据库
```
建库
>create database testme
>go

(2)看当前数据库列表
>select* from SysDatabases

(3)看当前数据表
> use库名
> select * from sysobiects where xtype='U'
>go

(4)看表的内容
> select*from表名; 
>go

```


#### 6.4 DataX 导入导出案例
创建表并插入数据
```
>create table student(id int,name varchar(25))
>go
>insert into student values(1,'zhangsan')
>go
```

##### 6.4.1 读取SQLServer数据导入到MySQL
参考上面即可


##### 6.4.2 读取SQLServer数据导入到HDFS

参考上面即可



### 七、DB2

#### 7.1 什么是DB2
DB2是 IBM 公司于1983年研制的一种关系型数据库系统(Relational DatabaseManagement System)，主要应用于大型应用系统，具有较好的可伸缩性。DB2是 IBM推出的第二个关系型数据库，所以称为 db2。DB2提供了高层次的数据利用性、完整性、安全性、并行性、可恢复性，以及小规模到大规模应用程序的执行能力，具有与平台无关的基本功能和SQL命令运行环境。可以同时在不同操作系统使用,包括Linux.UNIX和Windows。


#### 7.2 DB2数据库对象关系
- 1、instance，同一台机器上可以安装多个DB2 instance。
- 2、database，同一个instance下面可以创建有多个database。
- 3、schema,同一个database下面可以配置多个schema。
- 4、table，同一个schema下可以创建多个table。



#### 7.3 安装前准备

##### 7.3.1 安装依赖
```
yum install -y bc binutils compat-libcapl compat-libstdc++33 elfutils-libelf elfutils-libelf-devel fontconfig-devel glibc glibc-devel ksh libaio libaio-devel libX11libXau libXi libXtst libXrender libXrender-devel libgcc libstdc++ libstdc++-devel libxcb make smartmontools sysstat kmod* gcc-c++ compat-libstdc++-33
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226110331786-2098053651.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226161328126-388651018.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226161903937-389368940.png)


##### 7.3.1 修改配置文件sysctl.conf
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
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226110729554-1197381596.png)


##### 7.3.3 修改配置文件limits.conf
```
vim /etc/security/limits.conf


在文件末尾追加(* 表示是所有用户)

* soft nproc 65536
* hard nproc 65536
* soft nofile 65536
* hard nofile 65536

```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211224150629972-1821052922.png)

>重启机器生效

##### 7.3.4 上传安装包并解压
```
tar -zxvf v11.5.4_linuxx64_server_dec.tar.gz -C /opt/module/

tar -zxvf v11.5.4_linuxx64_server_dec.tar.gz
chmod 777 server_dec
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226161055990-2003068737.png)


#### 7.4 安装
在root用户下操作
1）执行预检查命令
```
./db2prereqcheck -l -s //检查环境
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226161210840-1837073617.png)

之前的依赖安装完成后执行检查环境还是缺少
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226162453393-2084868640.png)

```
yum install libstdc++.so.6
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226162559034-1998735106.png)


![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226162638505-690638874.png)


2）执行安装
```
./db2_install
```
>pureScala可不装，装集群才需要单机版不用

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226162934547-1377078669.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226163300087-892672081.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226163334941-1644607803.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226163609755-341732581.png)


```
[root@localhost server_dec]# ./db2prereqcheck -l -s
Requirement not matched for DB2 database "Server" . Version: "11.5.4.0". Summary of prerequisites that are not met on the current system:    DBT3514W  The db2prereqcheck utility failed to find the following 32-bit library file: "/lib/libpam.so*". 


Requirement not matched for DB2 database "Server" with pureScale feature . Version: "11.5.4.0". Summary of prerequisites that are not met on the current system: DBT3613E  The db2prereqcheck utility failed to verify the prerequisites for TSA. Ensure your machine meets all the TSA installation prerequisites. 

DBT3507E  The db2prereqcheck utility failed to find the following package or file: "kernel-source". 


[root@localhost server_dec]# ./db2_install
Requirement not matched for DB2 database "Server" . Version: "11.5.4.0". 

Summary of prerequisites that are not met on the current system: 

   DBT3514W  The db2prereqcheck utility failed to find the following 32-bit library file: "/lib/libpam.so*". 


Read the license agreement file in the db2/license directory.

***********************************************************
To accept those terms, enter "yes". Otherwise, enter "no" to cancel the install process. [yes/no]
yes
 
 
Default directory for installation of products - /opt/ibm/db2/V11.5

***********************************************************
Install into default directory (/opt/ibm/db2/V11.5) ? [yes/no] 
yes
 
 
Specify one of the following keywords to install DB2 products.

  SERVER 
  CONSV 
  CLIENT 
  RTCL 
 
Enter "help" to redisplay product names.

Enter "quit" to exit.

***********************************************************
SERVER
***********************************************************
Do you want to install the DB2 pureScale Feature? [yes/no] 
no
Requirement not matched for DB2 database "Server" . Version: "11.5.4.0". 

Summary of prerequisites that are not met on the current system: 

   DBT3514W  The db2prereqcheck utility failed to find the following 32-bit library file: "/lib/libpam.so*". 


DB2 installation is being initialized.

 Total number of tasks to be performed: 58 
Total estimated time for all tasks to be performed: 2692 second(s) 

Task #1 start
Description: Checking license agreement acceptance 
Estimated time 1 second(s) 
Task #1 end 

Task #2 start
Description: Base Client Support for installation with root privileges 
Estimated time 3 second(s) 
Task #2 end 

Task #3 start
Description: Product Messages - English 
Estimated time 14 second(s) 
Task #3 end 

Task #4 start
Description: Base client support 
Estimated time 290 second(s) 
Task #4 end 

Task #5 start
Description: Java Runtime Support 
Estimated time 216 second(s) 
Task #5 end 

Task #6 start
Description: Java Help (HTML) - English 
Estimated time 7 second(s) 
Task #6 end 

Task #7 start
Description: Base server support for installation with root privileges 
Estimated time 6 second(s) 
Task #7 end 

Task #8 start
Description: Global Secure ToolKit 
Estimated time 74 second(s) 
Task #8 end 

Task #9 start
Description: Java support 
Estimated time 11 second(s) 
Task #9 end 

Task #10 start
Description: SQL procedures 
Estimated time 3 second(s) 
Task #10 end 

Task #11 start
Description: ICU Utilities 
Estimated time 59 second(s) 
Task #11 end 

Task #12 start
Description: Java Common files 
Estimated time 18 second(s) 
Task #12 end 

Task #13 start
Description: Base server support 
Estimated time 572 second(s) 
Task #13 end 

Task #14 start
Description: Control Center Help (HTML) - English 
Estimated time 13 second(s) 
Task #14 end 

Task #15 start
Description: Relational wrappers common 
Estimated time 3 second(s) 
Task #15 end 

Task #16 start
Description: DB2 data source support 
Estimated time 6 second(s) 
Task #16 end 

Task #17 start
Description: ODBC data source support 
Estimated time 309 second(s) 
Task #17 end 

Task #18 start
Description: Teradata data source support 
Estimated time 3 second(s) 
Task #18 end 

Task #19 start
Description: Spatial Extender server support 
Estimated time 21 second(s) 
Task #19 end 

Task #20 start
Description: Scientific Data Sources 
Estimated time 5 second(s) 
Task #20 end 

Task #21 start
Description: JDBC data source support 
Estimated time 267 second(s) 
Task #21 end 

Task #22 start
Description: IBM Software Development Kit (SDK) for Java(TM) 
Estimated time 49 second(s) 
Task #22 end 

Task #23 start
Description: DB2 LDAP support 
Estimated time 4 second(s) 
Task #23 end 

Task #24 start
Description: DB2 Instance Setup wizard 
Estimated time 23 second(s) 
Task #24 end 

Task #25 start
Description: Structured file data sources 
Estimated time 5 second(s) 
Task #25 end 

Task #26 start
Description: Integrated Flash Copy Support 
Estimated time 3 second(s) 
Task #26 end 

Task #27 start
Description: Oracle data source support 
Estimated time 4 second(s) 
Task #27 end 

Task #28 start
Description: Connect support 
Estimated time 3 second(s) 
Task #28 end 

Task #29 start
Description: Application data sources 
Estimated time 4 second(s) 
Task #29 end 

Task #30 start
Description: Spatial Extender client 
Estimated time 3 second(s) 
Task #30 end 

Task #31 start
Description: SQL Server data source support 
Estimated time 4 second(s) 
Task #31 end 

Task #32 start
Description: Communication support - TCP/IP 
Estimated time 3 second(s) 
Task #32 end 

Task #33 start
Description: Tivoli SA MP 
Estimated time 300 second(s) 
Task #33 end 

Task #34 start
Description: Base application development tools 
Estimated time 32 second(s) 
Task #34 end 

Task #35 start
Description: DB2 Update Service 
Estimated time 4 second(s) 
Task #35 end 

Task #36 start
Description: Parallel Extension 
Estimated time 3 second(s) 
Task #36 end 

Task #37 start
Description: EnterpriseDB code 
Estimated time 3 second(s) 
Task #37 end 

Task #38 start
Description: Replication tools 
Estimated time 59 second(s) 
Task #38 end 

Task #39 start
Description: Sample database source 
Estimated time 4 second(s) 
Task #39 end 

Task #40 start
Description: itlm 
Estimated time 3 second(s) 
Task #40 end 

Task #41 start
Description: DB2 Text Search 
Estimated time 107 second(s) 
Task #41 end 

Task #42 start
Description: Command Line Processor Plus 
Estimated time 7 second(s) 
Task #42 end 

Task #43 start
Description: Sybase data source support 
Estimated time 3 second(s) 
Task #43 end 

Task #44 start
Description: Informix data source support 
Estimated time 4 second(s) 
Task #44 end 

Task #45 start
Description: Federated Data Access Support 
Estimated time 3 second(s) 
Task #45 end 

Task #46 start
Description: First Steps 
Estimated time 3 second(s) 
Task #46 end 

Task #47 start
Description: Product Signature for DB2 Server Edition 
Estimated time 7 second(s) 
Task #47 end 

Task #48 start
Description: Guardium Installation Manager Client 
Estimated time 36 second(s) 
Task #48 end 

Task #49 start
Description: Setting DB2 library path 
Estimated time 180 second(s) 
Task #49 end 

Task #50 start
Description: Installing or updating DB2 HA scripts for IBM Tivoli System Automation for Multiplatforms (Tivoli SA MP) 
Estimated time 40 second(s) 
Task #50 end 

Task #51 start
Description: Executing control tasks 
Estimated time 20 second(s) 
Task #51 end 

Task #52 start
Description: Updating global registry 
Estimated time 20 second(s) 
Task #52 end 

Task #53 start
Description: Starting DB2 Fault Monitor 
Estimated time 10 second(s) 
Task #53 end 

Task #54 start
Description: Updating the db2ls and db2greg link 
Estimated time 1 second(s) 
Task #54 end 

Task #55 start
Description: Registering DB2 licenses 
Estimated time 5 second(s) 
Task #55 end 

Task #56 start
Description: Setting default global profile registry variables 
Estimated time 1 second(s) 
Task #56 end 

Task #57 start
Description: Initializing instance list 
Estimated time 5 second(s) 
Task #57 end 

Task #58 start
Description: Registering DB2 Update Service 
Estimated time 30 second(s) 
Task #58 end 

Task #59 start
Description: Updating global profile registry 
Estimated time 3 second(s) 
Task #59 end 

The execution completed with warnings.

For more information see the DB2 installation log at
"/tmp/db2_install.log.741".
[root@localhost server_dec]#

```

3）查看许可
```
此处是安装目录下的/adm/db2licm -l

默认安装的位置
/opt/ibm/db2/V11.5/

/opt/ibm/db2/V11.5/adm/db2licm -l
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226164100179-851669879.png)


4）添加组和用户
db2inst1：所有实例者
db2fenc1：受防护用户
```
groupadd -g 2000 db2iadm1
groupadd -g 2001 db2fadm1
useradd -m -g db2iadm1 -d /home/db2inst1 db2inst1
useradd -m -g db2fadm1 -d /home/db2fenc1 db2fenc1

//12345678
passwd db2inst1
passwd db2fenc1
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226113747011-658816980.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226164524002-987085819.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226164910876-1755304521.png)



5）创建用户
```
cd /opt/ibm/db2/V11.5/instance
./db2icrt -p 50000 -u db2fenc1 db2inst1
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226164804646-515889641.png)

 ![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226165009843-29561105.png)


6）创建实例、开启服务
```
su - db2inst1
db2sampl

db2start
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226171725625-1737907147.png)


7）连接
```
db2
db2=>connect to sample
select * from staff
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226171858520-692910184.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226171948437-1497547276.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226172044234-323577673.png)


使用DBeaver连接DB2数据库
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226172640287-1915916389.png)



8）创建表和插入数据
```
CREATE TABLE STUDENT(ID int, NAME varchar(20));
INSERT INTO STUDENT VALUES(1, 'zhangsan');
INSERT INTO STUDENT VALUES(2, 'lisi');
commit;
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226172937502-23552996.png)


#### 7.5 DataX导入导出案例
##### 7.5.1 注册DB2驱动
datax暂时没有独立插件支持db2，需要使用通用的使用rdbmsreader或rdbmswriter。

-  1）注册reader的 db2驱动
```
#备份原始的json文件
cp /data/datax/plugin/reader/rdbmsreader/plugin.json plugin/reader/rdbmsreader/plugin-bak.json

vim /data/datax/plugin/reader/rdbmsreader/plugin.json
#在 drivers里添加db2的驱动类
"drivers":["dm.jdbc.driver.DmDriver", "com.sybase.jdbc3.jdbc.SybDriver","com.edb.Driver", "com.ibm.db2.jcc.DB2Driver"]
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226185547110-987790898.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226185642352-1890651729.png)


2）注册writer 的db2驱动
```
#备份原始的json文件
cp /data/datax/plugin/writer/rdbmswriter/plugin.json plugin/writer/rdbmswriter/plugin-bak.json

vim /data/datax/plugin/writer/rdbmswriter/plugin.json
#在 drivers里添加db2的驱动类
"drivers":["dm.jdbc.driver.DmDriver", "com.sybase.jdbc3.jdbc.SybDriver","com.edb.Driver", "com.ibm.db2.jcc.DB2Driver"]
```
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226185948777-2082021548.png)

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226185920060-1131067649.png)


##### 7.5.2 读取DB2的数据导入到HDFS
- 查看json模板   bin/datax.py -r rdbmsreader -w hdfswriter
```
{
    "job": {
        "content": [
            {
                "reader": {
                    "name": "rdbmsreader", 
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
                    "name": "hdfswriter", 
                    "parameter": {
                        "column": [], 
                        "compress": "", 
                        "defaultFS": "", 
                        "fieldDelimiter": "", 
                        "fileName": "", 
                        "fileType": "", 
                        "path": "", 
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


##### 7.5.3 读取DB2的数据导入到MySQL
- 查看json模板   bin/datax.py -r rdbmsreader -w mysqlwriter
- 修改json模板数据
```
{
    "job": {
        "content": [
            {
                "reader": {
                    "name": "rdbmsreader", 
                    "parameter": {
                        "column": ["*"], 
                        "connection": [
                            {
                                "jdbcUrl": ["jdbc:db2://192.168.56.10:50000/sample"], 
                                "table": [
                                    "STUDENT"
                                ]
                            }
                        ], 
                        "password": "12345678", 
                        "username": "db2inst1", 
                        "where": ""
                    }
                }, 
                "writer": {
                    "name": "mysqlwriter",
                    "parameter": {
                        "column": [
                            "id",
                            "name"
                        ],
                        "connection": [
                            {
                                "jdbcUrl": "jdbc:mysql://192.168.1.37:3306/test",
                                "table": ["db22mysql"]
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

- 执行job任务
```
bin/datax.py ./job/db22mysqljob.json
```

![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226190353571-31064466.png)


- 执行log
```
[root@localhost datax]# bin/datax.py ./job/db22mysqljob.json 

DataX (DATAX-OPENSOURCE-3.0), From Alibaba !
Copyright (C) 2010-2017, Alibaba Group. All Rights Reserved.


2021-12-26 18:48:32.817 [main] INFO  VMInfo - VMInfo# operatingSystem class => sun.management.OperatingSystemImpl
2021-12-26 18:48:32.888 [main] INFO  Engine - the machine info  => 

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


2021-12-26 18:48:32.958 [main] INFO  Engine - 
{
	"content":[
		{
			"reader":{
				"name":"rdbmsreader",
				"parameter":{
					"column":[
						"*"
					],
					"connection":[
						{
							"jdbcUrl":[
								"jdbc:db2://192.168.56.10:50000/sample"
							],
							"table":[
								"STUDENT"
							]
						}
					],
					"password":"********",
					"username":"db2inst1",
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
							"jdbcUrl":"jdbc:mysql://192.168.1.37:3306/test",
							"table":[
								"db22mysql"
							]
						}
					],
					"password":"******",
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

2021-12-26 18:48:32.997 [main] WARN  Engine - prioriy set to 0, because NumberFormatException, the value is: null
2021-12-26 18:48:33.000 [main] INFO  PerfTrace - PerfTrace traceId=job_-1, isEnable=false, priority=0
2021-12-26 18:48:33.001 [main] INFO  JobContainer - DataX jobContainer starts job.
2021-12-26 18:48:33.006 [main] INFO  JobContainer - Set jobId = 0
2021-12-26 18:48:35.457 [job-0] INFO  OriginalConfPretreatmentUtil - Available jdbcUrl:jdbc:db2://192.168.56.10:50000/sample.
2021-12-26 18:48:35.462 [job-0] WARN  OriginalConfPretreatmentUtil - 您的配置文件中的列配置存在一定的风险. 因为您未配置读取数据库表的列，当您的表字段个数、类型有变动时，可能影响任务正确性甚至会运行出错。请检查您的配置并作出修改.
2021-12-26 18:48:38.713 [job-0] INFO  OriginalConfPretreatmentUtil - table:[db22mysql] all columns:[
id,name
].
2021-12-26 18:48:38.739 [job-0] INFO  OriginalConfPretreatmentUtil - Write data [
insert INTO %s (id,name) VALUES(?,?)
], which jdbcUrl like:[jdbc:mysql://192.168.1.37:3306/test?yearIsDateType=false&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&rewriteBatchedStatements=true]
2021-12-26 18:48:38.740 [job-0] INFO  JobContainer - jobContainer starts to do prepare ...
2021-12-26 18:48:38.741 [job-0] INFO  JobContainer - DataX Reader.Job [rdbmsreader] do prepare work .
2021-12-26 18:48:38.741 [job-0] INFO  JobContainer - DataX Writer.Job [mysqlwriter] do prepare work .
2021-12-26 18:48:38.743 [job-0] INFO  JobContainer - jobContainer starts to do split ...
2021-12-26 18:48:38.743 [job-0] INFO  JobContainer - Job set Channel-Number to 1 channels.
2021-12-26 18:48:38.757 [job-0] INFO  JobContainer - DataX Reader.Job [rdbmsreader] splits to [1] tasks.
2021-12-26 18:48:38.758 [job-0] INFO  JobContainer - DataX Writer.Job [mysqlwriter] splits to [1] tasks.
2021-12-26 18:48:38.829 [job-0] INFO  JobContainer - jobContainer starts to do schedule ...
2021-12-26 18:48:38.857 [job-0] INFO  JobContainer - Scheduler starts [1] taskGroups.
2021-12-26 18:48:38.862 [job-0] INFO  JobContainer - Running by standalone Mode.
2021-12-26 18:48:38.902 [taskGroup-0] INFO  TaskGroupContainer - taskGroupId=[0] start [1] channels for [1] tasks.
2021-12-26 18:48:38.952 [taskGroup-0] INFO  Channel - Channel set byte_speed_limit to -1, No bps activated.
2021-12-26 18:48:38.953 [taskGroup-0] INFO  Channel - Channel set record_speed_limit to -1, No tps activated.
2021-12-26 18:48:39.011 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] taskId[0] attemptCount[1] is started
2021-12-26 18:48:39.048 [0-0-0-reader] INFO  CommonRdbmsReader$Task - Begin to read record by Sql: [select * from STUDENT 
] jdbcUrl:[jdbc:db2://192.168.56.10:50000/sample].
2021-12-26 18:48:39.910 [0-0-0-reader] INFO  CommonRdbmsReader$Task - Finished read record by Sql: [select * from STUDENT 
] jdbcUrl:[jdbc:db2://192.168.56.10:50000/sample].
2021-12-26 18:48:40.118 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] taskId[0] is successed, used[1113]ms
2021-12-26 18:48:40.119 [taskGroup-0] INFO  TaskGroupContainer - taskGroup[0] completed it's tasks.
2021-12-26 18:48:48.976 [job-0] INFO  StandAloneJobContainerCommunicator - Total 1 records, 9 bytes | Speed 0B/s, 0 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 0.000s |  All Task WaitReaderTime 0.801s | Percentage 100.00%
2021-12-26 18:48:48.976 [job-0] INFO  AbstractScheduler - Scheduler accomplished all tasks.
2021-12-26 18:48:48.977 [job-0] INFO  JobContainer - DataX Writer.Job [mysqlwriter] do post work.
2021-12-26 18:48:48.978 [job-0] INFO  JobContainer - DataX Reader.Job [rdbmsreader] do post work.
2021-12-26 18:48:48.978 [job-0] INFO  JobContainer - DataX jobId [0] completed successfully.
2021-12-26 18:48:48.979 [job-0] INFO  HookInvoker - No hook invoked, because base dir not exists or is a file: /data/datax/hook
2021-12-26 18:48:48.981 [job-0] INFO  JobContainer - 
	 [total cpu info] => 
		averageCpu                     | maxDeltaCpu                    | minDeltaCpu                    
		-1.00%                         | -1.00%                         | -1.00%
                        

	 [total gc info] => 
		 NAME                 | totalGCCount       | maxDeltaGCCount    | minDeltaGCCount    | totalGCTime        | maxDeltaGCTime     | minDeltaGCTime     
		 PS MarkSweep         | 0                  | 0                  | 0                  | 0.000s             | 0.000s             | 0.000s             
		 PS Scavenge          | 0                  | 0                  | 0                  | 0.000s             | 0.000s             | 0.000s             

2021-12-26 18:48:48.982 [job-0] INFO  JobContainer - PerfTrace not enable!
2021-12-26 18:48:48.982 [job-0] INFO  StandAloneJobContainerCommunicator - Total 1 records, 9 bytes | Speed 0B/s, 0 records/s | Error 0 records, 0 bytes |  All Task WaitWriterTime 0.000s |  All Task WaitReaderTime 0.801s | Percentage 100.00%
2021-12-26 18:48:48.984 [job-0] INFO  JobContainer - 
任务启动时刻                    : 2021-12-26 18:48:33
任务结束时刻                    : 2021-12-26 18:48:48
任务总计耗时                    :                 15s
任务平均流量                    :                0B/s
记录写入速度                    :              0rec/s
读出记录总数                    :                   1
读写失败总数                    :                   0

[root@localhost datax]# 

```


#### 7.6 Windows版本安装DB2
https://blog.csdn.net/u012288582/article/details/80870630



### 八、执行流程源码分析

#### 8.1 总体流程
![](https://img2020.cnblogs.com/blog/1231979/202112/1231979-20211226230745590-1146003628.png)


- 黄色:Job部分的执行阶段,
- 蓝色:Task部分的执行阶段,
- 绿色：框架执行阶段。
