package com.lframework.starter.web.core.components.upload.client.impl;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.FileUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.redis.RedisHandler;
import com.lframework.starter.web.core.components.upload.client.UploadClient;
import com.lframework.starter.web.core.components.upload.client.config.LocalUploadConfig;
import com.lframework.starter.web.core.components.upload.client.dto.UploadDto;
import com.lframework.starter.web.inner.service.SysConfService;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class LocalUploadClient implements UploadClient {

  private LocalUploadConfig config;

  public LocalUploadClient(LocalUploadConfig config) {
    this.config = config;
  }

  @Override
  public UploadDto upload(InputStream is, List<String> locations,
      String fileName) {
    String domain = config.getDomain();
    String baseUrl = config.getUrl();
    String location = config.getLocation();

    Assert.notNull(is);
    Assert.notBlank(domain);
    Assert.notBlank(baseUrl);
    Assert.notBlank(location);
    Assert.notBlank(fileName);

    if (domain.endsWith("/")) {
      domain = domain.substring(0, domain.length() - 1);
    }
    if (!baseUrl.startsWith("/")) {
      baseUrl = "/" + baseUrl;
    }

    String url = domain + baseUrl;

    if (StringUtil.isEmpty(url)) {
      url = StringPool.EMPTY_STR;
    }

    if (!url.endsWith("/")) {
      url = url + "/";
    }

    if (!location.endsWith(File.separator)) {
      // 路径不是以分隔符结尾
      location = location + File.separator;
    }

    //文件全路径 比如/upload/datas/2022/01/01/
    String fullPath = location + (CollectionUtil.isEmpty(locations) ?
        StringPool.EMPTY_STR :
        CollectionUtil.join(locations, File.separator)) + File.separator;

    File dest = FileUtil.touch(fullPath, fileName);

    FileUtil.writeFromStream(is, dest);

    UploadDto dto = new UploadDto();
    dto.setUrl(url + (CollectionUtil.isEmpty(locations) ?
        StringPool.EMPTY_STR :
        CollectionUtil.join(locations, "/") + "/") + fileName);
    dto.setObjectName(fullPath + fileName);
    dto.setUploadType("LOCAL");
    return dto;
  }

  @Override
  public String generatePresignedUrl(String objectName, long expiration) {

    String baseUrl = config.getDomain();
    String uri = "/download/security";
    String sign = IdUtil.getUUID();

    SysConfService sysConfService = ApplicationUtil.getBean(SysConfService.class);
    Long expire = sysConfService.getLong("security-upload.sign-url-expired", 600L);

    RedisHandler redisHandler = ApplicationUtil.getBean(RedisHandler.class);
    redisHandler.hset("security-upload", sign, objectName,
        expire * 1000L);

    return baseUrl + uri + "?sign=" + sign;
  }
}
