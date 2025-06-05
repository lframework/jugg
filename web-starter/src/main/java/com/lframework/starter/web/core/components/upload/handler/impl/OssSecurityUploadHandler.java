package com.lframework.starter.web.core.components.upload.handler.impl;

import com.lframework.starter.web.core.components.upload.client.UploadClient;
import com.lframework.starter.web.core.components.upload.client.dto.UploadDto;
import com.lframework.starter.web.core.components.upload.client.impl.OssUploadClient;
import com.lframework.starter.web.core.components.upload.client.config.OssUploadConfig;
import com.lframework.starter.web.core.components.upload.handler.SecurityUploadHandler;
import com.lframework.starter.web.inner.service.SysConfService;
import com.lframework.starter.web.core.utils.JsonUtil;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class OssSecurityUploadHandler implements SecurityUploadHandler {

  @Autowired
  private SysConfService sysConfService;

  @Override
  public String getType() {
    return "OSS";
  }

  @Override
  public UploadDto upload(InputStream is, List<String> locations, String fileName) {
    String configStr = sysConfService.findRequiredByKey("security-upload.oss.config");
    OssUploadConfig config = JsonUtil.parseObject(configStr, OssUploadConfig.class);

    UploadClient uploadClient = new OssUploadClient(config);

    UploadDto dto = uploadClient.upload(is, locations, fileName);

    return createSecurityRecord(dto);
  }

  @Override
  public String generatePresignedUrl(String objectName, long expiration) {
    String configStr = sysConfService.findRequiredByKey("security-upload.oss.config");
    OssUploadConfig config = JsonUtil.parseObject(configStr, OssUploadConfig.class);

    UploadClient uploadClient = new OssUploadClient(config);

    return uploadClient.generatePresignedUrl(objectName, expiration);
  }
}
