package com.lframework.starter.mq.core.dto;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class ExportTaskSummaryDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  private Integer unFinishedCount;

  private Integer successCount;

  private Integer failCount;
}
