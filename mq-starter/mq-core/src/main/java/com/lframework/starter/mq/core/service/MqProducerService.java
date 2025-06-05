package com.lframework.starter.mq.core.service;

import com.lframework.starter.mq.core.dto.AddExportTaskDto;
import com.lframework.starter.mq.core.dto.ExecuteExportTaskDto;
import com.lframework.starter.web.core.service.BaseService;
import com.lframework.starter.web.inner.dto.message.SysMailMessageDto;
import com.lframework.starter.web.inner.dto.message.SysSiteMessageDto;
import com.lframework.starter.web.inner.dto.notify.SysNotifyDto;

public interface MqProducerService extends BaseService {

  /**
   * 发送消息通知
   *
   * @param dto
   */
  void createSysNotify(SysNotifyDto dto);

  /**
   * 发送邮件消息
   *
   * @param dto
   */
  void createSysMailMessage(SysMailMessageDto dto);

  /**
   * 发送站内信
   *
   * @param dto
   */
  void createSysSiteMessage(SysSiteMessageDto dto);

  /**
   * 新增导出任务
   *
   * @param dto
   */
  void addExportTask(AddExportTaskDto dto);

  /**
   * 执行导出任务
   *
   * @param dto
   */
  void executeExportTask(ExecuteExportTaskDto dto);
}
