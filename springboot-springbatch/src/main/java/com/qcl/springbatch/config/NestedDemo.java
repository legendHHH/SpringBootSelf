package com.qcl.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 嵌套测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/5
 */
@Configuration
public class NestedDemo {

    /**
     * 注入的是具体的某一个job的bean
     */
    @Autowired
    private Job childJobOne;

    @Autowired
    private Job childJobTwo;

    @Autowired
    private JobLauncher jobLauncher;

    /**
     * 注入创建任务对象的对象
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job myParentJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return jobBuilderFactory.get("myParentJob")
                .start(childJob1(jobRepository, transactionManager))
                .next(childJob2(jobRepository, transactionManager))
                .build();
    }

    /**
     * 返回的是Job类型的step，特殊的step
     *
     * @return
     */
    private Step childJob1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob1"))
                //指定job
                .job(childJobOne)
                //使用启动父Job的启动对象
                .launcher(jobLauncher)
                //持久化
                .repository(jobRepository)
                //事务管理器
                .transactionManager(transactionManager)
                .build();
    }

    private Step childJob2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob2"))
                //指定job
                .job(childJobTwo)
                //使用启动父Job的启动对象
                .launcher(jobLauncher)
                //持久化
                .repository(jobRepository)
                //事务管理器
                .transactionManager(transactionManager)
                .build();
    }
}
