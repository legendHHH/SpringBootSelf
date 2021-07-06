package com.qcl.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 决策器的使用
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/5
 */
@Configuration
@EnableBatchProcessing
public class JobExecutionDeciderDemo {

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
    public Step deciderDemoStep1() {
        return stepBuilderFactory.get("deciderDemoStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hello World deciderDemoStep1");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step deciderDemoStep2() {
        return stepBuilderFactory.get("deciderDemoStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hello World deciderDemoStep2");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step deciderDemoStep3() {
        return stepBuilderFactory.get("deciderDemoStep3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hello World deciderDemoStep3");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    /**
     * 创建决策器
     *
     * @return
     */
    @Bean
    public JobExecutionDecider jobExecutionDecider() {
        return new MyDecider();

    }

    @Bean
    public Job deciderDemoJob() {
        //deciderDemoJob 指定job的名称
        return jobBuilderFactory.get("deciderDemoJob")
                //决策器
                .start(deciderDemoStep1())
                .next(jobExecutionDecider())
                .from(jobExecutionDecider()).on("even").to(deciderDemoStep2())
                .from(jobExecutionDecider()).on("odd").to(deciderDemoStep3())
                .from(deciderDemoStep3()).on("*").to(jobExecutionDecider())
                .end()
                .build();
    }
}
