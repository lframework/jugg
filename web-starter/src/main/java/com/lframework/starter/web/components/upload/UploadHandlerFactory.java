package com.lframework.starter.web.components.upload;

import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.upload.handler.UploadHandler;
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
}
