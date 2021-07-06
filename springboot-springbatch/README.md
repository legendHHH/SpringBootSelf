## SpringBatch批处理入门

### 应用场景
- 数据量大，从数万到数百万甚至上亿不等

- 整个过程全部自动化，并预留一定接口进行自定义配置

- 这样的应用通常是周期性运行，比如按日、周、月运行

- 对数据处理的准确性要求高，并且需要容错机制、回滚机制、完善的日志监控等。



### 什么是SpringBatch
[SpringBatch官方文档](https://spring.io/projects/spring-batch)
[SpringBatch中文文档](https://www.docs4dev.com/docs/zh/spring-batch/4.1.x/reference)

Spring batch是一个轻量级的全面的批处理框架


Spring Batch是一个轻量级的、完善的批处理框架,旨在帮助企业建立健壮、高效的批处理应用。Spring Batch是Spring的一个子项目,使用Java语言并基于Spring框架为基础开发,使得已经使用Spring框架的开发者或者企业更容易访问和利用企业服务。
Spring Batch提供了大量可重用的组件包括了日志、追踪、事务、任务作业统计、任务重启、跳过、重复、资源管理。对于大数据量和高性能的批处理任务,Spring Batch同样提供了高级功能和特性来支持比如分区功能、远程功能。总之,通过Spring Batch能够支持简单的、o杂的和大数据量的批处理作业,
Spring Batch是一个批处理应用框架,不是调度框架;但需要和调度框架合作来构建完成的批处理任务。它只关注批处理任务相关的问题如事务、并发、监控.执行等,并不提供相应的调度功能。如果需要使用调用框架,在商业软件和开源软件中已经有很多优秀的企业级调度框架(如Quartz、Tivoli、Control-M、Cron等)可以使用。


