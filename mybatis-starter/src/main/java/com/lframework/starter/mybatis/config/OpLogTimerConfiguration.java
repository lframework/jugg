package com.lframework.starter.mybatis.config;

import com.lframework.starter.mybatis.service.IOpLogsService;
import com.lframework.starter.web.components.qrtz.QrtzHandler;
import com.lframework.starter.web.components.qrtz.QrtzJob;
import java.time.LocalDateTime;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OpLogTimerConfiguration implements ApplicationListener<ApplicationReadyEvent> {

  private static final String JOB_NAME = "OP_LOG_TIMER";

  private static final String JOB_GROUP_NAME = "OP_LOG_TIMER_GROUP";

  private static final String TRIGGER_NAME = "OP_LOG_TIMER_TRIGGER";

  private static final String TRIGGER_GROUP_NAME = "OP_LOG_TIMER_TRIGGER_GROUP";

  @Value("${op-logs.enabled:'true'}")
  private Boolean enabled;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

    // 没有使用@Bean的配置方式是因为当enabled==false时，需要移除定时任务
    JobDetail jobDetail = QrtzHandler.getJob(JOB_NAME, JOB_GROUP_NAME);
    if (jobDetail == null) {
      // 没有任务
      if (!this.enabled) {
        return;
      }

      QrtzHandler.addJob(JOB_NAME, JOB_GROUP_NAME, OpLogClearJob.class, TRIGGER_NAME,
          TRIGGER_GROUP_NAME,
          "0 0 * * * ? *");
    } else {
      if (!this.enabled) {
        QrtzHandler.deleteJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME);
      }
    }
  }

  public static class OpLogClearJob extends QrtzJob {

    @Autowired
    private IOpLogsService opLogsService;

    /**
     * 操作日志保留天数
     */
    @Value("${op-logs.retain-days:7}")
    private Integer retainDays;

    @Override
    public void onExecute(JobExecutionContext context) {

      LocalDateTime now = LocalDateTime.now();
      LocalDateTime endTime = now.minusDays(retainDays);

      opLogsService.clearLogs(endTime);
    }
  }
}
