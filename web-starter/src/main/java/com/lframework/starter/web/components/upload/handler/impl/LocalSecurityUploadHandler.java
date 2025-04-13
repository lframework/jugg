package com.lframework.starter.web.components.upload.handler.impl;

import com.lframework.starter.web.components.redis.RedisHandler;
import com.lframework.starter.web.components.upload.client.UploadClient;
import com.lframework.starter.web.components.upload.client.config.LocalUploadConfig;
import com.lframework.starter.web.components.upload.client.dto.UploadDto;
import com.lframework.starter.web.components.upload.client.impl.LocalUploadClient;
import com.lframework.starter.web.components.upload.handler.SecurityUploadHandler;
import com.lframework.starter.web.config.properties.SecurityUploadProperties;
import com.lframework.starter.web.config.properties.UploadProperties;
import com.lframework.starter.web.service.SysConfService;
import java.io.InputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class LocalSecurityUploadHandler implements SecurityUploadHandler {

  @Autowired
  private UploadProperties uploadProperties;

  @Autowired
  private SecurityUploadProperties securityUploadProperties;

  @Override
  public String getType() {
    return "LOCAL";
  }

  @Override
  public UploadDto upload(InputStream is, List<String> locations, String fileName) {

    LocalUploadConfig config = new LocalUploadConfig();
    config.setDomain(securityUploadProperties.getDomain());
    config.setUrl(uploadProperties.getUrl());
    config.setLocation(securityUploadProperties.getLocation());
    UploadClient uploadClient = new LocalUploadClient(config);

    return uploadClient.upload(is, locations, fileName);
  }

  @Override
  public String generatePresignedUrl(String objectName, long expiration) {
    LocalUploadConfig config = new LocalUploadConfig();
    config.setDomain(securityUploadProperties.getDomain());
    config.setUrl(uploadProperties.getUrl());
    config.setLocation(securityUploadProperties.getLocation());
    UploadClient uploadClient = new LocalUploadClient(config);

    return uploadClient.generatePresignedUrl(objectName, expiration);
  }
}
