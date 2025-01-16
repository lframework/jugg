package com.lframework.starter.web.sign;

import com.lframework.starter.web.utils.ApplicationUtil;

public class DefaultCheckSignFactory implements CheckSignFactory {

  @Override
  public CheckSignHandler getInstance() {
    return ApplicationUtil.getBean(CheckSignHandler.class);
  }
}
