package com.lframework.starter.mq.core.config;

import com.lframework.starter.mq.core.controller.ExportTaskController;
import com.lframework.starter.mq.core.impl.ExportTaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
@Import({
    ExportTaskServiceImpl.class,
    ExportTaskController.class,
})
@MapperScan("com.lframework.starter.mq.core.**.mappers")
public class MqBeanAutoConfiguration implements EnvironmentAware {

  @Override
  public void setEnvironment(Environment environment) {
    log.info("mq-core加载完成");
  }
}
