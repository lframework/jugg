package com.lframework.starter.mq.rabbitmq.impl;

import com.lframework.starter.mq.core.dto.ExecuteExportTaskDto;
import com.lframework.starter.mq.core.producer.MqProducer;
import com.lframework.starter.mq.core.service.MqProducerService;
import com.lframework.starter.mq.rabbitmq.constants.RabbitMqConstants;
import com.lframework.starter.mq.core.dto.AddExportTaskDto;
import com.lframework.starter.web.inner.dto.message.SysMailMessageDto;
import com.lframework.starter.web.inner.dto.message.SysSiteMessageDto;
import com.lframework.starter.web.inner.dto.notify.SysNotifyDto;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitMqProducerServiceImpl implements MqProducerService {

  @Autowired
  private MqProducer mqProducer;

  @Override
  public void createSysNotify(SysNotifyDto dto) {
    mqProducer.sendMessage(RabbitMqConstants.SYS_NOTIFY, dto);
  }

  @Override
  public void createSysMailMessage(SysMailMessageDto dto) {
    mqProducer.sendMessage(RabbitMqConstants.SYS_MAIL_MESSAGE, dto);
  }

  @Override
  public void createSysSiteMessage(SysSiteMessageDto dto) {
    mqProducer.sendMessage(RabbitMqConstants.SYS_SITE_MESSAGE, dto);
  }

  @Override
  public void addExportTask(AddExportTaskDto dto) {
    mqProducer.sendMessage(RabbitMqConstants.ADD_EXPORT_TASK, dto);
  }

  @Override
  public void executeExportTask(ExecuteExportTaskDto dto) {
    mqProducer.sendMessage(RabbitMqConstants.EXECUTE_EXPORT_TASK, dto);
  }
}
