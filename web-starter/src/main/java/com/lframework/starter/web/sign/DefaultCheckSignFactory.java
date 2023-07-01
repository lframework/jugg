package com.lframework.starter.web.sign;

import com.lframework.starter.web.common.utils.ApplicationUtil;
import org.springframework.stereotype.Component;

public class DefaultCheckSignFactory implements CheckSignFactory {

  @Override
  public CheckSignHandler getInstance() {
    return ApplicationUtil.getBean(CheckSignHandler.class);
  }
}
