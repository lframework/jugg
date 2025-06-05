package com.lframework.starter.mq.core.dto;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class ExecuteExportTaskDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  private String taskId;

  private String token;
}
