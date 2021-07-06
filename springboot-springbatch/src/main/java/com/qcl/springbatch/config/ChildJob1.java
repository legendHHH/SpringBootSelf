package com.qcl.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Job嵌套
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/5
 */
//@EnableBatchProcessing
@Configuration
public class ChildJob1 {

    /**
     * 注入创建任务对象的对象
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /**
     * 任务的执行由Step决定
     * 注入创建Step对象的对象
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step childJob1DemoStep1() {
        return stepBuilderFactory.get("childJob1DemoStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hello World childJob1DemoStep1");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }


    @Bean
    public Job childJobOne() {
        return jobBuilderFactory.get("childJobOne")
                .start(childJob1DemoStep1())
                .build();
    }
}
