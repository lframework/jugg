package com.lframework.starter.web.components.upload.handler.impl;

import com.lframework.starter.web.components.upload.client.UploadClient;
import com.lframework.starter.web.components.upload.client.config.ObsUploadConfig;
import com.lframework.starter.web.components.upload.client.dto.UploadDto;
import com.lframework.starter.web.components.upload.client.impl.ObsUploadClient;
import com.lframework.starter.web.components.upload.handler.SecurityUploadHandler;
import com.lframework.starter.web.service.SysConfService;
import com.lframework.starter.web.utils.JsonUtil;
import java.io.InputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ObsSecurityUploadHandler implements SecurityUploadHandler {

  @Autowired
  private SysConfService sysConfService;

  @Override
  public String getType() {
    return "OBS";
  }

  @Override
  public UploadDto upload(InputStream is, List<String> locations, String fileName) {
    String configStr = sysConfService.findRequiredByKey("security-upload.obs.config");
    ObsUploadConfig config = JsonUtil.parseObject(configStr, ObsUploadConfig.class);

    UploadClient uploadClient = new ObsUploadClient(config);
    return uploadClient.upload(is, locations, fileName);
  }

  @Override
  public String generatePresignedUrl(String objectName, long expiration) {
    String configStr = sysConfService.findRequiredByKey("security-upload.obs.config");
    ObsUploadConfig config = JsonUtil.parseObject(configStr, ObsUploadConfig.class);

    UploadClient uploadClient = new ObsUploadClient(config);

    return uploadClient.generatePresignedUrl(objectName, expiration);
  }
}
