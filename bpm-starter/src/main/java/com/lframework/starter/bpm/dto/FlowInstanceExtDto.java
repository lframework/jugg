package com.lframework.starter.bpm.dto;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class FlowInstanceExtDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务类型
   */
  private String bizType;

  /**
   * 业务标识
   */
  private String bizFlag;
}
