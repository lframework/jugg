package com.lframework.starter.web.core.components.upload.client.impl;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.upload.client.UploadClient;
import com.lframework.starter.web.core.components.upload.client.config.OssUploadConfig;
import com.lframework.starter.web.core.components.upload.client.dto.UploadDto;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OssUploadClient implements UploadClient {

  private OssUploadConfig config;

  public OssUploadClient(OssUploadConfig config) {
    this.config = config;
  }

  @Override
  public UploadDto upload(InputStream is, List<String> locations, String fileName) {

    String customUrl = config.getCustomUrl();
    String endPoint = config.getEndpoint();
    String internalEndPoint = config.getInternalEndPoint();
    String accessKeyId = config.getAccessKeyId();
    String accessKeySecret = config.getAccessKeySecret();
    String bucketName = config.getBucketName();

    Assert.notBlank(endPoint);
    Assert.notBlank(bucketName);
    Assert.notBlank(accessKeyId);
    Assert.notBlank(accessKeySecret);

    OSS ossClient = new OSSClientBuilder().build(
        StringUtil.isBlank(internalEndPoint) ? endPoint
            : internalEndPoint, accessKeyId,
        accessKeySecret);

    String objectName = (CollectionUtil.isEmpty(locations) ? StringPool.EMPTY_STR
        : (StringUtil.join("/", locations) + "/")) + fileName;
    try {
      // 这里不判断桶是否存在，通过控台创建
      ossClient.putObject(bucketName, objectName, is);

      UploadDto dto = new UploadDto();

      dto.setObjectName(objectName);

      if (StringUtil.isNotBlank(customUrl)) {
        dto.setUrl(customUrl + (customUrl.endsWith("/") ? StringPool.EMPTY_STR
            : "/") + objectName);
      } else {
        dto.setUrl("https://" + bucketName + "." + endPoint + "/" + objectName);
      }
      dto.setUploadType("OSS");
      return dto;
    } finally {
      if (ossClient != null) {
        ossClient.shutdown();
      }
    }
  }

  @Override
  public String generatePresignedUrl(String objectName, long expiration) {
    String customUrl = config.getCustomUrl();
    String endPoint = config.getEndpoint();
    String accessKeyId = config.getAccessKeyId();
    String accessKeySecret = config.getAccessKeySecret();
    String bucketName = config.getBucketName();

    Assert.notBlank(endPoint);
    Assert.notBlank(bucketName);
    Assert.notBlank(accessKeyId);
    Assert.notBlank(accessKeySecret);
    if (!StringUtil.isBlank(customUrl)) {
      // 这里只判断customUrl即可，涉及到下载，不会使用内网endPoint
      endPoint = customUrl;
    }

    ClientBuilderConfiguration configuration = new ClientBuilderConfiguration();
    // 请注意，设置true开启CNAME选项。
    configuration.setSupportCname(true);
    // 显式声明使用 V4 签名算法
    configuration.setSignatureVersion(SignVersion.V4);

    // 生成预签名URL。
    GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName,
        HttpMethod.GET);

    // 设置下载行为（强制下载而非预览）及文件名
    try {
      request.getResponseHeaders().setContentDisposition("attachment;filename=" + URLEncoder.encode(
          objectName.substring(objectName.lastIndexOf("/") + 1), "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }

    // 设置过期时间。
    LocalDateTime now = LocalDateTime.now();
    request.setExpiration(DateUtil.toDate(now.plusSeconds(expiration)));

    // 通过HTTP GET请求生成预签名URL。
    OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId,
        accessKeySecret, configuration);

    return ossClient.generatePresignedUrl(request).toString();
  }
}
