package com.lframework.starter.web.core.components.sign;

import com.lframework.starter.web.core.utils.ApplicationUtil;

public class DefaultCheckSignFactory implements CheckSignFactory {

  @Override
  public CheckSignHandler getInstance() {
    return ApplicationUtil.getBean(CheckSignHandler.class);
  }
}
