package com.lframework.starter.web.core.components.upload.handler.impl;

import com.lframework.starter.web.core.components.upload.client.UploadClient;
import com.lframework.starter.web.core.components.upload.client.config.LocalUploadConfig;
import com.lframework.starter.web.core.components.upload.client.dto.UploadDto;
import com.lframework.starter.web.core.components.upload.client.impl.LocalUploadClient;
import com.lframework.starter.web.core.components.upload.handler.UploadHandler;
import com.lframework.starter.web.config.properties.UploadProperties;
import java.io.InputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class LocalUploadHandler implements UploadHandler {

  @Autowired
  private UploadProperties uploadProperties;

  @Override
  public String getType() {
    return "LOCAL";
  }

  @Override
  public UploadDto upload(InputStream is, List<String> locations, String fileName) {

    LocalUploadConfig config = new LocalUploadConfig();
    config.setDomain(uploadProperties.getDomain());
    config.setUrl(uploadProperties.getUrl());
    config.setLocation(uploadProperties.getLocation());
    UploadClient uploadClient = new LocalUploadClient(config);

    return uploadClient.upload(is, locations, fileName);
  }
}
