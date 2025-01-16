package com.lframework.starter.web.components.upload.handler.impl;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.upload.handler.UploadHandler;
import com.lframework.starter.web.service.SysConfService;
import com.lframework.starter.web.utils.JsonUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import java.io.InputStream;
import java.util.List;
import lombok.Data;
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
  public String upload(InputStream is, List<String> locations, String fileName) {
    String configStr = sysConfService.findRequiredByKey("upload.cos.config");
    CosConfig config = JsonUtil.parseObject(configStr, CosConfig.class);

    Assert.notBlank(config.getCustomUrl());
    Assert.notBlank(config.getRegion());
    Assert.notBlank(config.getBucketName());
    Assert.notBlank(config.getSecretId());
    Assert.notBlank(config.getSecretKey());

    String objectName = StringUtil.join("/", locations) + fileName;

    TransferManager transferManager = null;
    try {
      transferManager = createTransferManager(config);

      PutObjectRequest request = new PutObjectRequest(config.getBucketName(), objectName, is,
          new ObjectMetadata());
      Upload upload = transferManager.upload(request);
      upload.waitForUploadResult();

      return config.getCustomUrl() + (config.getCustomUrl().endsWith("/") ? StringPool.EMPTY_STR
          : "/") + objectName;
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    } finally {
      if (transferManager != null) {
        transferManager.shutdownNow(true);
      }
    }
  }

  private TransferManager createTransferManager(CosConfig config) {

    COSClient cosClient = createCosClient(config);

    TransferManager transferManager = new TransferManager(cosClient);

    return transferManager;
  }

  private COSClient createCosClient(CosConfig config) {

    String secretId = config.getSecretId();
    String secretKey = config.getSecretKey();
    COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

    ClientConfig clientConfig = new ClientConfig();
    clientConfig.setRegion(new Region(config.getRegion()));

    clientConfig.setHttpProtocol(HttpProtocol.https);

    // 设置 socket 读取超时，默认 30s
    clientConfig.setSocketTimeout(30 * 1000);
    // 设置建立连接超时，默认 30s
    clientConfig.setConnectionTimeout(30 * 1000);

    return new COSClient(cred, clientConfig);
  }

  @Data
  public static class CosConfig {

    private String customUrl;

    private String region;

    private String secretId;

    private String secretKey;

    private String bucketName;
  }
}
