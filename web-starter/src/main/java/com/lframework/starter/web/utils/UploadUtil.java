package com.lframework.starter.web.utils;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.FileUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.upload.UploadHandlerFactory;
import com.lframework.starter.web.components.upload.handler.UploadHandler;
import com.lframework.starter.web.service.SysConfService;
import java.io.IOException;
import java.io.InputStream;
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

    Assert.notNull(file);
    SysConfService sysConfService = ApplicationUtil.getBean(SysConfService.class);
    String uploadTypeStr = sysConfService.findByKey("upload.type", "LOCAL");

    UploadHandler uploadHandler = UploadHandlerFactory.getInstance(uploadTypeStr);

    String suffix = FileUtil.getSuffix(file.getOriginalFilename());
    String fileName = IdUtil.getUUID();
    String fullFileName =
        fileName + (StringUtil.isEmpty(suffix) ? StringPool.EMPTY_STR : ("." + suffix));

    try (InputStream is = file.getInputStream()) {
      return uploadHandler.upload(is, getDefaultLocations(), fullFileName);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
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
