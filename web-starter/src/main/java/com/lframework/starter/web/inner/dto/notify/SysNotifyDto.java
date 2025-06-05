package com.lframework.starter.web.inner.dto.notify;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class SysNotifyDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 动态参数
   */
  private Object variables;

  /**
   * 业务类型
   */
  private Integer bizType;

  /**
   * 通知组ID
   */
  private String notifyGroupId;

  /**
   * 创建人ID 如果是空表示由系统自动发起
   */
  private String createUserId;
}
