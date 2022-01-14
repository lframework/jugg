package com.lframework.starter.mybatis.config;

import com.lframework.starter.mybatis.service.IOpLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

import static com.lframework.starter.mybatis.config.OpLogConfiguration.OP_LOG_THREAD_POOL_NAME;

@EnableAsync
@Configuration
@ConditionalOnProperty(value = "op-logs.enabled", matchIfMissing = true)
public class OpLogTimer {

    @Autowired
    private IOpLogsService opLogsService;

    /**
     * 操作日志保留天数
     */
    @Value("${op-logs.retain-days:7}")
    private Integer retainDays;

    /**
     * 清除过期日志
     * 每一小时执行一次
     */
    @Async(OP_LOG_THREAD_POOL_NAME)
    @Scheduled(fixedDelay = 3600000L)
    public void clearLogs() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = now.minusDays(retainDays);

        opLogsService.clearLogs(endTime);
    }
}
