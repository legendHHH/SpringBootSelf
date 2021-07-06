package com.qcl.springbatch.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * 决策器的创建
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/5
 */
public class MyDecider implements JobExecutionDecider {

    private int count;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        //如果变量的值是偶数返回另外一个值
        count++;
        if (count % 2 == 0) {
            return new FlowExecutionStatus("even");
        } else {
            return new FlowExecutionStatus("odd");
        }

    }
}
