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
 * 调度配置Job的创建和使用
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/5
 */
//@Configuration
//@EnableBatchProcessing
public class JobDemo {

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
    public Job jobDemoJob() {
        //jobDemoJob 指定job的名称
        return jobBuilderFactory.get("jobDemoJob")
                //多个step
                /*.start(step1())
                .next(step2())
                .next(step3())
                .build();*/

                //从哪一个开始完成执行下一步
                .start(step1()).on("COMPLETED").to(step2())
                //fail()、stopAndRestart()
                .from(step2()).on("COMPLETED").to(step3())
                .from(step3()).end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hello World step1");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hello World step2");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hello World step3");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
}
