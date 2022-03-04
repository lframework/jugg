package com.lframework.starter.web.components.qrtz;

import com.lframework.common.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时任务
 */
@Slf4j
public abstract class QrtzJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("开始执行定时任务【{}】", this.getClass().getName());

        try {
            this.onExecute(context);
        } catch (Exception e) {
            if (e instanceof ClientException) {
                log.warn("执行定时任务【{}】失败", this.getClass().getName());
                log.warn(e.getMessage(), e);
            } else {
                log.error("执行定时任务【{}】失败", this.getClass().getName());
                log.error(e.getMessage(), e);
            }
            throw e;
        }
    }

    protected abstract void onExecute(JobExecutionContext context);
}
