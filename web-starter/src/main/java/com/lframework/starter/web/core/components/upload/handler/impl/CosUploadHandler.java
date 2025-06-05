package com.lframework.starter.web.core.components.upload.handler.impl;

import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.web.core.components.upload.client.UploadClient;
import com.lframework.starter.web.core.components.upload.client.config.CosUploadConfig;
import com.lframework.starter.web.core.components.upload.client.dto.UploadDto;
import com.lframework.starter.web.core.components.upload.client.impl.CosUploadClient;
import com.lframework.starter.web.core.components.upload.handler.UploadHandler;
import com.lframework.starter.web.inner.service.SysConfService;
import com.lframework.starter.web.core.utils.JsonUtil;
import java.io.InputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CosUploadHandler implements UploadHandler {

  @Autowired
  private SysConfService sysConfService;

  @Override
  public String getType() {
    return "COS";
  }

  @Override
  public UploadDto upload(InputStream is, List<String> locations, String fileName) {
    String configStr = sysConfService.findRequiredByKey("upload.cos.config");
    CosUploadConfig config = JsonUtil.parseObject(configStr, CosUploadConfig.class);

    Assert.notBlank(config.getEndpoint());
    Assert.notBlank(config.getRegion());
    Assert.notBlank(config.getBucketName());
    Assert.notBlank(config.getSecretId());
    Assert.notBlank(config.getSecretKey());

    UploadClient uploadClient = new CosUploadClient(config);

    return uploadClient.upload(is, locations, fileName);
  }
}
