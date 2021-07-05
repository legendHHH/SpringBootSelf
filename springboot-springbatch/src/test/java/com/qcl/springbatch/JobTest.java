package com.qcl.springbatch;

import com.qcl.springbatch.config.JobConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 调度配置测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/5
 */
@RunWith(SpringRunner.class)
@SpringBatchTest
@ContextConfiguration(classes = {JobConfiguration.class})
public class JobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Before
    public void clearMetadata() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void testJob() throws Exception {
        // given
        JobParameters jobParameters =
                jobLauncherTestUtils.getUniqueJobParameters();

        // when
        JobExecution jobExecution =
                jobLauncherTestUtils.launchJob(jobParameters);

        // then
        Assert.assertEquals(ExitStatus.COMPLETED,
                jobExecution.getExitStatus());
    }

}