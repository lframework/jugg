package com.lframework.starter.web.components.upload.handler.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.upload.handler.UploadHandler;
import com.lframework.starter.web.service.SysConfService;
import com.lframework.starter.web.utils.JsonUtil;
import java.io.InputStream;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

public class OssUploadHandler implements UploadHandler {

  @Autowired
  private SysConfService sysConfService;

  @Override
  public String getType() {
    return "OSS";
  }

  @Override
  public String upload(InputStream is, List<String> locations, String fileName) {
    String configStr = sysConfService.findRequiredByKey("upload.oss.config");
    OssConfig config = JsonUtil.parseObject(configStr, OssConfig.class);

    Assert.notBlank(config.getEndpoint());
    Assert.notBlank(config.getBucketName());
    Assert.notBlank(config.getAccessKeyId());
    Assert.notBlank(config.getAccessKeySecret());

    OSS ossClient = new OSSClientBuilder().build(
        StringUtil.isBlank(config.getInternalEndPoint()) ? config.getEndpoint()
            : config.getInternalEndPoint(), config.getAccessKeyId(),
        config.getAccessKeySecret());

    String objectName = StringUtil.join("/", locations) + fileName;
    try {
      // 这里不判断桶是否存在，通过控台创建
      ossClient.putObject(config.getBucketName(), objectName, is);

      if (StringUtil.isNotBlank(config.getCustomUrl())) {
        return config.getCustomUrl() + (config.getCustomUrl().endsWith("/") ? StringPool.EMPTY_STR
            : "/") + objectName;
      }

      return "https://" + config.getBucketName() + "." + config.getEndpoint() + "/" + objectName;
    } finally {
      if (ossClient != null) {
        ossClient.shutdown();
      }
    }
  }

  @Data
  public static class OssConfig {

    private String customUrl;

    private String endpoint;

    private String internalEndPoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;
  }
}
