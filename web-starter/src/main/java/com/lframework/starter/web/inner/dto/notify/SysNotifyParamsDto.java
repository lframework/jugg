package com.lframework.starter.web.inner.dto.notify;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class SysNotifyParamsDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 动态参数
   */
  private Object variables;
}
