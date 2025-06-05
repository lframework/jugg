package com.lframework.starter.web.core.components.upload;

import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.web.core.components.upload.handler.SecurityUploadHandler;
import com.lframework.starter.web.core.components.upload.handler.UploadHandler;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import java.util.Map;

public class UploadHandlerFactory {

  public static UploadHandler getInstance(String type) {
    Assert.notBlank(type);

    Map<String, UploadHandler> uploadHandlerMap = ApplicationUtil.getBeansOfType(
        UploadHandler.class);
    for (UploadHandler value : uploadHandlerMap.values()) {
      if (value.getType().equalsIgnoreCase(type)) {
        return value;
      }
    }

    throw new DefaultSysException("没有找到" + type + "对应的UploadHandler");
  }

  public static SecurityUploadHandler getSecurityInstance(String type) {
    Assert.notBlank(type);

    Map<String, SecurityUploadHandler> uploadHandlerMap = ApplicationUtil.getBeansOfType(
        SecurityUploadHandler.class);
    for (SecurityUploadHandler value : uploadHandlerMap.values()) {
      if (value.getType().equalsIgnoreCase(type)) {
        return value;
      }
    }

    throw new DefaultSysException("没有找到" + type + "对应的UploadHandler");
  }
}
