package com.lframework.starter.mq.core.utils;

import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.mq.core.components.export.ExportTaskWorker;
import com.lframework.starter.mq.core.service.MqProducerService;
import com.lframework.starter.web.core.components.security.UserTokenResolver;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.mq.core.dto.AddExportTaskDto;
import java.time.LocalDateTime;

public class ExportTaskUtil {

  public static void exportTask(String taskName,
      Class<? extends ExportTaskWorker<?, ?, ?>> clazz,
      Object params) {
    MqProducerService mqProducerService = ApplicationUtil.getBean(MqProducerService.class);
    UserTokenResolver userTokenResolver = ApplicationUtil.getBean(UserTokenResolver.class);
    AddExportTaskDto dto = new AddExportTaskDto();
    dto.setName(taskName + "_" + DateUtil.formatDateTime(LocalDateTime.now(), "yyyyMMddHHmmss"));
    dto.setReqClassName(clazz.getName());
    dto.setReqParams(JsonUtil.toJsonString(params));
    dto.setToken(userTokenResolver.getToken());

    mqProducerService.addExportTask(dto);
  }
}
