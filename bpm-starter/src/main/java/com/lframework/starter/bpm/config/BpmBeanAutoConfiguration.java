package com.lframework.starter.bpm.config;

import com.lframework.starter.bpm.controller.BpmSelectorController;
import com.lframework.starter.bpm.controller.FlowCategoryController;
import com.lframework.starter.bpm.controller.FlowDefinitionController;
import com.lframework.starter.bpm.controller.FlowInstanceController;
import com.lframework.starter.bpm.controller.FlowTaskController;
import com.lframework.starter.bpm.handlers.BpmChartExtService;
import com.lframework.starter.bpm.handlers.BpmHandlerSelectService;
import com.lframework.starter.bpm.handlers.BpmPermissionHandler;
import com.lframework.starter.bpm.handlers.exception.FlowExceptionConverter;
import com.lframework.starter.bpm.impl.FlowCategoryServiceImpl;
import com.lframework.starter.bpm.impl.FlowCuApproveHisServiceImpl;
import com.lframework.starter.bpm.impl.FlowCuInstanceServiceImpl;
import com.lframework.starter.bpm.impl.FlowDefinitionWrapperServiceImpl;
import com.lframework.starter.bpm.impl.FlowInstanceWrapperServiceImpl;
import com.lframework.starter.bpm.impl.FlowTaskWrapperServiceImpl;
import com.lframework.starter.bpm.listeners.BpmGlobalListener;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
@Import({
    BpmSelectorController.class,
    FlowCategoryController.class,
    FlowDefinitionController.class,
    FlowTaskController.class,
    FlowInstanceController.class,
    FlowCategoryServiceImpl.class,
    FlowDefinitionWrapperServiceImpl.class,
    FlowTaskWrapperServiceImpl.class,
    FlowCuInstanceServiceImpl.class,
    FlowInstanceWrapperServiceImpl.class,
    FlowCuApproveHisServiceImpl.class,
    BpmChartExtService.class,
    BpmGlobalListener.class,
})
@MapperScan("com.lframework.starter.bpm.**.mappers")
public class BpmBeanAutoConfiguration implements EnvironmentAware {

  @Bean
  public BpmHandlerSelectService bpmHandlerSelectService() {
    return new BpmHandlerSelectService();
  }

  @Bean
  public BpmPermissionHandler bpmPermissionHandler() {
    return new BpmPermissionHandler();
  }

  @Bean
  public FlowExceptionConverter flowExceptionConverter() {
    return new FlowExceptionConverter();
  }

  @Override
  public void setEnvironment(Environment environment) {
    log.info("bpm-starter加载完成");
  }
}
