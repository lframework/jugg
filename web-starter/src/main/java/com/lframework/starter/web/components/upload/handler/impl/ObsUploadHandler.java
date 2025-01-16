package com.lframework.starter.web.components.upload.handler.impl;

import cn.hutool.core.util.URLUtil;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.upload.handler.UploadHandler;
import com.lframework.starter.web.service.SysConfService;
import com.lframework.starter.web.utils.JsonUtil;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ObsUploadHandler implements UploadHandler {

  @Autowired
  private SysConfService sysConfService;

  @Override
  public String getType() {
    return "OBS";
  }

  @Override
  public String upload(InputStream is, List<String> locations, String fileName) {
    String configStr = sysConfService.findRequiredByKey("upload.obs.config");
    ObsConfig config = JsonUtil.parseObject(configStr, ObsConfig.class);

    Assert.notBlank(config.getEndpoint());
    Assert.notBlank(config.getBucketName());
    Assert.notBlank(config.getAk());
    Assert.notBlank(config.getSk());

    String objectName = StringUtil.join("/", locations) + fileName;

    try (ObsClient obsClient = new ObsClient(config.getAk(), config.getSk(),
        config.getEndpoint())) {
      // 不判断桶是否存在，建议桶在控台创建
      PutObjectResult result = obsClient.putObject(config.getBucketName(), objectName, is);

      if (StringUtil.isNotBlank(config.getCustomUrl())) {
        return config.getCustomUrl() + (config.getCustomUrl().endsWith("/") ? StringPool.EMPTY_STR
            : "/") + objectName;
      }

      return URLUtil.decode(result.getObjectUrl());
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }

  @Data
  public static class ObsConfig {

    private String customUrl;

    private String endpoint;

    private String ak;

    private String sk;

    private String bucketName;
  }
}
