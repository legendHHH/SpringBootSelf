package com.qcl.quartz.extend;

import org.quartz.*;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/3/23
 */
public class JobDetailExt implements JobDetail {

    @Override
    public JobKey getKey() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Class<? extends Job> getJobClass() {
        return null;
    }

    @Override
    public JobDataMap getJobDataMap() {
        return null;
    }

    @Override
    public boolean isDurable() {
        return false;
    }

    @Override
    public boolean isPersistJobDataAfterExecution() {
        return false;
    }

    @Override
    public boolean isConcurrentExectionDisallowed() {
        return false;
    }

    @Override
    public boolean requestsRecovery() {
        return false;
    }

    @Override
    public Object clone() {
        return null;
    }

    @Override
    public JobBuilder getJobBuilder() {
        return null;
    }
}
