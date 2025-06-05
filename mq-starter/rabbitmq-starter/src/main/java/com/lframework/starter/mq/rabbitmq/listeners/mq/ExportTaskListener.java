package com.lframework.starter.mq.rabbitmq.listeners.mq;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.ReflectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mq.core.dto.AddExportTaskDto;
import com.lframework.starter.mq.core.dto.ExecuteExportTaskDto;
import com.lframework.starter.mq.core.entity.ExportTask;
import com.lframework.starter.mq.core.enums.ExportTaskStatus;
import com.lframework.starter.mq.core.handlers.ExportTaskHandler;
import com.lframework.starter.mq.core.service.ExportTaskService;
import com.lframework.starter.mq.core.service.MqProducerService;
import com.lframework.starter.mq.rabbitmq.constants.RabbitMqStringPool;
import com.lframework.starter.web.core.components.security.AbstractUserDetails;
import com.lframework.starter.web.core.components.security.SecurityUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.inner.service.SysConfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component("exportTaskListener")
public class ExportTaskListener {

  @Autowired
  private ExportTaskService exportTaskService;

  @Autowired
  private MqProducerService mqProducerService;

  @Autowired
  private SysConfService sysConfService;

  @RabbitListener(bindings = {
      @QueueBinding(value = @Queue(value = RabbitMqStringPool.ADD_EXPORT_TASK_QUEUE), key = RabbitMqStringPool.ADD_EXPORT_TASK_ROUTING_KEY, exchange = @Exchange(value = RabbitMqStringPool.ADD_EXPORT_TASK_EXCHANGE))}, concurrency = "2")
  public void addExportTask(Message<AddExportTaskDto> message) {

    AddExportTaskDto dto = message.getPayload();

    log.info("接收到新增导出任务MQ message = {}", dto);

    AbstractUserDetails currentUser = SecurityUtil.getUserByToken(dto.getToken());
    if (currentUser == null) {
      log.info("导出用户已退出登录，无法导出");
      return;
    }

    try {
      SecurityUtil.setCurrentUser(currentUser);

      // 新增导出任务
      ExportTask task = new ExportTask();
      task.setId(IdUtil.getId());
      task.setName(dto.getName());
      task.setStatus(ExportTaskStatus.CREATED);
      task.setReqClassName(dto.getReqClassName());
      task.setReqParams(dto.getReqParams());

      task.setReqParamsSign(SecureUtil.md5(dto.getReqClassName() + ":" + dto.getReqParams()));

      exportTaskService.create(task);

      ExecuteExportTaskDto taskDto = new ExecuteExportTaskDto();
      taskDto.setTaskId(task.getId());
      taskDto.setToken(dto.getToken());

      mqProducerService.executeExportTask(taskDto);

    } finally {
      SecurityUtil.removeCurrentUser();
    }
  }

  @RabbitListener(bindings = {
      @QueueBinding(value = @Queue(value = RabbitMqStringPool.EXECUTE_EXPORT_TASK_QUEUE), key = RabbitMqStringPool.EXECUTE_EXPORT_TASK_ROUTING_KEY, exchange = @Exchange(value = RabbitMqStringPool.EXECUTE_EXPORT_TASK_EXCHANGE))})
  public void executeExportTask(Message<ExecuteExportTaskDto> message) {

    ExecuteExportTaskDto dto = message.getPayload();

    log.info("接收到执行导出任务MQ message = {}", dto);

    ExportTask task = exportTaskService.getById(dto.getTaskId());
    AbstractUserDetails currentUser = SecurityUtil.getUserByToken(dto.getToken());

    try {
      if (currentUser == null) {
        throw new DefaultClientException("导出用户已退出登录，无法导出");
      }

      SecurityUtil.setCurrentUser(currentUser);

      Wrapper<ExportTask> checkWrapper = Wrappers.lambdaQuery(ExportTask.class)
          .eq(ExportTask::getReqParamsSign, task.getReqParamsSign())
          .in(ExportTask::getStatus, ExportTaskStatus.CREATED, ExportTaskStatus.EXPORTING)
          .ne(ExportTask::getId, task.getId());
      if (exportTaskService.count(checkWrapper) > 0) {
        throw new DefaultClientException("导出任务重复，请勿重复导出。");
      }

      ExportTaskHandler exportTaskHandler = new ExportTaskHandler(task.getId(),
          ReflectUtil.newInstance(task.getReqClassName()),
          sysConfService.getLong("export.timeout", 600L));
      exportTaskHandler.execute(task.getReqParams());
    } catch (ClientException e) {
      exportTaskService.setFail(task.getId(),
          StringUtil.isBlank(e.getMessage()) ? e.toString() : e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      exportTaskService.setFail(task.getId(),
          StringUtil.isBlank(e.getMessage()) ? e.toString() : e.getMessage());
    } finally {
      SecurityUtil.removeCurrentUser();
    }
  }
}
