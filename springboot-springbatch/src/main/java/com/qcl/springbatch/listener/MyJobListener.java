package com.qcl.springbatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * 监听器的使用
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/25
 */
public class MyJobListener implements JobExecutionListener {

    /**
     * job执行之前执行
     *
     * @param jobExecution
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("jobInstance--->jobName：" + jobExecution.getJobInstance().getJobName() + "       jobParameters：" + jobExecution.getJobParameters().getParameters() + "      before.....");
    }

    /**
     * job执行之后执行
     *
     * @param jobExecution
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("jobInstance--->jobName：" + jobExecution.getJobInstance().getJobName() + "     jobParameters：" + jobExecution.getJobParameters().getParameters() + "        after.....");
    }
}
