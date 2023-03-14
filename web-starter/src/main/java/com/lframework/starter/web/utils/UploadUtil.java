package com.lframework.starter.web.utils;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.FileUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class UploadUtil {

  /**
   * 上传文件
   *
   * @param file
   * @return
   */
  public static String upload(MultipartFile file) {

    String domain = ApplicationUtil.getProperty("upload.domain");
    if (domain.endsWith("/")) {
      domain = domain.substring(0, domain.length() - 1);
    }
    String baseUrl = ApplicationUtil.getProperty("upload.url");
    if (!baseUrl.startsWith("/")) {
      baseUrl = "/" + baseUrl;
    }
    String baseLocation = ApplicationUtil.getProperty("upload.location");

    return upload(file, baseLocation, getDefaultLocations(), IdUtil.getUUID(), domain + baseUrl);
  }

  /**
   * 上传文件
   *
   * @param file         文件
   * @param baseLocation 基础存储路径
   * @param locations    路径 比如想把文件存在${baseLocation}/datas/2022/01/01目录处，此时locations = ['datas',
   *                     '2022', '01', '01']
   * @param fileName     文件名
   * @param baseUrl      基础url
   * @return
   */
  public static String upload(MultipartFile file, String baseLocation, List<String> locations,
      String fileName,
      String baseUrl) {

    Assert.notNull(file);
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

    // 扩展名 比如jpg
    String suffix = FileUtil.getSuffix(file.getOriginalFilename());
    // 文件名 比如a.jpg
    String fullFileName =
        fileName + (StringUtil.isEmpty(suffix) ? StringPool.EMPTY_STR : ("." + suffix));

    File dest = FileUtil.touch(fullPath, fullFileName);

    try {
      file.transferTo(dest);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }

    return baseUrl + (CollectionUtil.isEmpty(locations) ?
        StringPool.EMPTY_STR :
        CollectionUtil.join(locations, "/") + "/") + fullFileName;
  }

  /**
   * 获取默认的locations
   *
   * @return
   */
  private static List<String> getDefaultLocations() {

    LocalDate now = LocalDate.now();
    List<String> locations = new ArrayList<>();
    locations.add(DateUtil.formatDate(now, "yyyy"));
    locations.add(DateUtil.formatDate(now, "MM"));
    locations.add(DateUtil.formatDate(now, "dd"));

    return locations;
  }
}
