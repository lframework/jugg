package com.lframework.starter.bpm.service;

import com.lframework.starter.bpm.entity.FlowInstanceWrapper;
import com.lframework.starter.web.core.service.BaseMpService;

public interface FlowInstanceWrapperService extends BaseMpService<FlowInstanceWrapper> {

  /**
   * 是否可以重新发起
   *
   * @param businessId
   * @return
   */
  Boolean canRestart(String businessId);
}
