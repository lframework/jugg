package com.lframework.starter.web.components.upload.handler.impl;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.FileUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.config.properties.UploadProperties;
import com.lframework.starter.web.components.upload.handler.UploadHandler;
import com.lframework.starter.web.utils.IdUtil;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class LocalUploadHandler implements UploadHandler {

  @Autowired
  private UploadProperties uploadProperties;

  @Override
  public String getType() {
    return "LOCAL";
  }

  @Override
  public String upload(InputStream is, List<String> locations, String fileName) {
    String domain = uploadProperties.getDomain();
    if (domain.endsWith("/")) {
      domain = domain.substring(0, domain.length() - 1);
    }
    String baseUrl = uploadProperties.getUrl();
    if (!baseUrl.startsWith("/")) {
      baseUrl = "/" + baseUrl;
    }
    String baseLocation = uploadProperties.getLocation();

    return upload(is, baseLocation, locations, IdUtil.getUUID(), domain + baseUrl);
  }

  /**
   * 上传文件
   *
   * @param is           输入流
   * @param baseLocation 基础存储路径
   * @param locations    路径 比如想把文件存在${baseLocation}/datas/2022/01/01目录处，此时locations = ['datas',
   *                     '2022', '01', '01']
   * @param fileName     文件名
   * @param baseUrl      基础url
   * @return
   */
  private String upload(InputStream is, String baseLocation, List<String> locations,
      String fileName,
      String baseUrl) {

    Assert.notNull(is);
    Assert.notBlank(baseLocation);
    Assert.notBlank(fileName);

    if (!baseLocation.endsWith(File.separator)) {
      // 路径不是以分隔符结尾
      baseLocation = baseLocation + File.separator;
    }

    if (StringUtil.isEmpty(baseUrl)) {
      baseUrl = StringPool.EMPTY_STR;
    }

    if (!baseUrl.endsWith("/")) {
      baseUrl = baseUrl + "/";
    }
    //文件全路径 比如/upload/datas/2022/01/01/
    String fullPath = baseLocation + (CollectionUtil.isEmpty(locations) ?
        StringPool.EMPTY_STR :
        CollectionUtil.join(locations, File.separator)) + File.separator;

    File dest = FileUtil.touch(fullPath, fileName);

    FileUtil.writeFromStream(is, dest);

    return baseUrl + (CollectionUtil.isEmpty(locations) ?
        StringPool.EMPTY_STR :
        CollectionUtil.join(locations, "/") + "/") + fileName;
  }
}
