package com.lframework.starter.web.core.components.upload.client.impl;

import cn.hutool.core.util.URLUtil;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.upload.client.UploadClient;
import com.lframework.starter.web.core.components.upload.client.config.ObsUploadConfig;
import com.lframework.starter.web.core.components.upload.client.dto.UploadDto;
import com.obs.services.ObsClient;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.PutObjectResult;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObsUploadClient implements UploadClient {

  private ObsUploadConfig config;

  public ObsUploadClient(ObsUploadConfig config) {
    this.config = config;
  }

  @Override
  public UploadDto upload(InputStream is, List<String> locations, String fileName) {

    String endPoint = config.getEndpoint();
    String bucketName = config.getBucketName();
    String ak = config.getAk();
    String sk = config.getSk();
    String customUrl = config.getCustomUrl();

    Assert.notBlank(endPoint);
    Assert.notBlank(bucketName);
    Assert.notBlank(ak);
    Assert.notBlank(sk);

    String objectName = (CollectionUtil.isEmpty(locations) ? StringPool.EMPTY_STR
        : (StringUtil.join("/", locations) + "/")) + fileName;

    try (ObsClient obsClient = new ObsClient(ak, sk,
        endPoint)) {
      // 不判断桶是否存在，建议桶在控台创建
      PutObjectResult result = obsClient.putObject(bucketName, objectName, is);

      UploadDto dto = new UploadDto();
      dto.setObjectName(objectName);
      if (StringUtil.isNotBlank(customUrl)) {
        dto.setUrl(customUrl + (customUrl.endsWith("/") ? StringPool.EMPTY_STR
            : "/") + objectName);
      } else {
        dto.setUrl(URLUtil.decode(result.getObjectUrl()));
      }

      dto.setUploadType("OBS");
      return dto;
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }

  @Override
  public String generatePresignedUrl(String objectName, long expiration) {
    String ak = config.getAk();
    String sk = config.getSk();
    String endPoint = config.getEndpoint();
    String bucketName = config.getBucketName();

    try (ObsClient obsClient = new ObsClient(ak, sk, endPoint)) {
      TemporarySignatureRequest req = new TemporarySignatureRequest();
      req.setExpires(expiration);
      req.setMethod(HttpMethodEnum.GET);
      req.setBucketName(bucketName);
      req.setObjectKey(objectName);

      TemporarySignatureResponse resp = obsClient.createTemporarySignature(req);
      return resp.getSignedUrl();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }
}
