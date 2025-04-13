package com.lframework.starter.web.components.upload.client.impl;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.upload.client.UploadClient;
import com.lframework.starter.web.components.upload.client.config.CosUploadConfig;
import com.lframework.starter.web.components.upload.client.dto.UploadDto;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.Headers;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.endpoint.UserSpecifiedEndpointBuilder;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.ResponseHeaderOverrides;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CosUploadClient implements UploadClient {

  private CosUploadConfig config;

  public CosUploadClient(CosUploadConfig config) {
    this.config = config;
  }

  @Override
  public UploadDto upload(InputStream is, List<String> locations, String fileName) {
    Assert.notBlank(config.getEndpoint());
    Assert.notBlank(config.getBucketName());
    Assert.notBlank(config.getSecretId());
    Assert.notBlank(config.getSecretKey());
    Assert.notBlank(config.getRegion());

    String objectName = StringUtil.join("/", locations) + fileName;

    TransferManager transferManager = null;
    try {
      transferManager = createTransferManager();

      PutObjectRequest request = new PutObjectRequest(config.getBucketName(), objectName, is,
          new ObjectMetadata());
      Upload upload = transferManager.upload(request);
      upload.waitForUploadResult();
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    } finally {
      if (transferManager != null) {
        transferManager.shutdownNow(true);
      }
    }
    String url = config.getEndpoint();
    if (!StringUtil.isBlank(config.getCustomUrl())) {
      url = config.getCustomUrl();
    }
    UploadDto dto = new UploadDto();
    dto.setUrl(url + (url.endsWith("/") ? StringPool.EMPTY_STR
        : "/") + objectName);
    dto.setObjectName(objectName);
    dto.setUploadType("COS");

    return dto;
  }

  @Override
  public String generatePresignedUrl(String objectName, long expiration) {
    Assert.notBlank(config.getBucketName());
    COSClient cosClient = createCosClient();
    LocalDateTime now = LocalDateTime.now();

    GeneratePresignedUrlRequest req =
        new GeneratePresignedUrlRequest(config.getBucketName(), objectName, HttpMethodName.GET);
    ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides();
    String responseContentDispositon = null;
    try {
      responseContentDispositon = "attachment;filename=" + URLEncoder.encode(
          objectName.substring(objectName.lastIndexOf("/") + 1), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
    String responseCacheControl = "no-cache";
    responseHeaders.setContentDisposition(responseContentDispositon);
    responseHeaders.setCacheControl(responseCacheControl);
    req.setResponseHeaders(responseHeaders);
    Date expirationDate = DateUtil.toDate(now.plusSeconds(expiration));
    req.setExpiration(expirationDate);
    req.putCustomRequestHeader(Headers.HOST,
        cosClient.getClientConfig().getEndpointBuilder()
            .buildGeneralApiEndpoint(config.getBucketName()));

    try {
      URL url = cosClient.generatePresignedUrl(req);
      return url.toString();
    } finally {
      cosClient.shutdown();
    }
  }

  private TransferManager createTransferManager() {

    COSClient cosClient = createCosClient();

    TransferManager transferManager = new TransferManager(cosClient);

    return transferManager;
  }

  private COSClient createCosClient() {

    COSCredentials cred = new BasicCOSCredentials(config.getSecretId(), config.getSecretKey());

    ClientConfig clientConfig = new ClientConfig();
    clientConfig.setRegion(new Region(config.getRegion()));

    String url = config.getEndpoint();
    if (!StringUtil.isBlank(config.getCustomUrl())) {
      url = config.getCustomUrl();
      // 设置自定义的域名
      UserSpecifiedEndpointBuilder endpointBuilder = new UserSpecifiedEndpointBuilder(
          url.replace("http://", "").replace("https://", ""), "service.cos.myqcloud.com");
      clientConfig.setEndpointBuilder(endpointBuilder);
    }

    clientConfig.setHttpProtocol(
        url.startsWith("https://") ? HttpProtocol.https : HttpProtocol.http);

    // 设置 socket 读取超时，默认 30s
    clientConfig.setSocketTimeout(30 * 1000);
    // 设置建立连接超时，默认 30s
    clientConfig.setConnectionTimeout(30 * 1000);

    return new COSClient(cred, clientConfig);
  }
}
