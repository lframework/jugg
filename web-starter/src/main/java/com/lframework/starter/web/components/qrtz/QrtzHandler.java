package com.lframework.starter.web.components.qrtz;

import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Map;

@Slf4j
public class QrtzHandler {

    private static final SchedulerFactoryBean SCHEDULER_FACTORY = ApplicationUtil.getBean(SchedulerFactoryBean.class);

    public static void addJob(Class<? extends QrtzJob> jobClass, String cron, Map<String, String> jobDatas) {

        addJob(null, null, jobClass, null, null, cron, jobDatas);
    }

    public static void addJob(Class<? extends QrtzJob> jobClass, String cron) {

        addJob(null, null, jobClass, null, null, cron, null);
    }

    public static void addJob(String jobName, String jobGroupName, Class<? extends QrtzJob> jobClass,
                              String triggerName, String triggerGroupName, String cron) {
        addJob(jobName, jobGroupName, jobClass, triggerName, triggerGroupName, cron, null);
    }

    /**
     * 添加任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param jobClass         任务类Class
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param cron             cron表达式
     * @param jobDatas         附加数据
     */
    public static void addJob(String jobName, String jobGroupName, Class<? extends QrtzJob> jobClass,
                              String triggerName, String triggerGroupName, String cron, Map<String, String> jobDatas) {
        try {
            Scheduler sched = SCHEDULER_FACTORY.getScheduler();
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
            if (!StringUtil.isBlank(jobName)) {
                jobBuilder.withIdentity(jobName, jobGroupName);
            }
            if (!CollectionUtil.isEmpty(jobDatas)) {
                jobDatas.forEach(jobBuilder::usingJobData);
            }

            JobDetail jobDetail = jobBuilder.build();

            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            if (!StringUtil.isBlank(triggerName)) {
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
            }

            triggerBuilder.startNow();
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            sched.scheduleJob(jobDetail, trigger);

            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }
    }

    /**
     * 查询触发器
     *
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @return
     */
    public static CronTrigger getTrigger(String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = SCHEDULER_FACTORY.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);

            return trigger;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }
    }

    /**
     * 查询任务
     *
     * @param jobName
     * @param jobGroupName
     * @return
     */
    public static JobDetail getJob(String jobName, String jobGroupName) {
        try {
            Scheduler sched = SCHEDULER_FACTORY.getScheduler();
            JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
            return jobDetail;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }
    }

    /**
     * 修改任务Cron
     *
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @param cron             cron表达式
     */
    public static void updateJobCron(String triggerName, String triggerGroupName, String cron) {
        try {
            Scheduler sched = SCHEDULER_FACTORY.getScheduler();
            CronTrigger trigger = getTrigger(triggerName, triggerGroupName);
            if (trigger == null) {
                return;
            }

            String oriCron = trigger.getCronExpression();
            if (!oriCron.equalsIgnoreCase(cron)) {
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                trigger = (CronTrigger) triggerBuilder.build();
                sched.rescheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName), trigger);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }
    }

    /**
     * 删除任务
     *
     * @param jobName          任务名称
     * @param jobGroupName     任务组名称
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     */
    public static void deleteJob(String jobName, String jobGroupName,
                                 String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = SCHEDULER_FACTORY.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            sched.pauseTrigger(triggerKey);
            sched.unscheduleJob(triggerKey);
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }
    }

    /**
     * 启动所有任务
     */
    public static void startJobs() {
        try {
            Scheduler sched = SCHEDULER_FACTORY.getScheduler();
            sched.start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }
    }

    /**
     * 关闭所有任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = SCHEDULER_FACTORY.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }
    }

    /**
     * 安全关闭
     *
     * @throws SchedulerException
     */
    public static void safeShutdown() throws SchedulerException {
        int executingJobSize = SCHEDULER_FACTORY.getScheduler().getCurrentlyExecutingJobs().size();
        log.info("当前运行任务个数：{}，等待完成后关闭", executingJobSize);
        SCHEDULER_FACTORY.getScheduler().shutdown(true);
    }
}
