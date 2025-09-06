package com.lframework.starter.web.core.utils;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.FileUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.components.upload.UploadHandlerFactory;
import com.lframework.starter.web.core.components.upload.client.dto.UploadDto;
import com.lframework.starter.web.core.components.upload.handler.SecurityUploadHandler;
import com.lframework.starter.web.core.components.upload.handler.UploadHandler;
import com.lframework.starter.web.inner.service.SysConfService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传工具类
 * 提供文件上传功能，支持多种上传处理器和文件类型验证
 * 包括文件上传、路径生成、文件类型检查等功能
 *
 * @author lframework@163.com
 */
@Slf4j
public class UploadUtil {

  /**
   * 上传文件（使用默认参数）
   * 使用默认设置上传文件，不进行文件类型限制
   *
   * @param file 上传文件，不能为null
   * @return 上传结果DTO
   * @throws DefaultSysException 当文件上传失败时抛出
   */
  public static UploadDto upload(MultipartFile file) {

    return upload(file, Collections.emptyList(), false);
  }

  /**
   * 上传文件（指定父路径）
   * 使用指定的父路径上传文件
   *
   * @param file 上传文件，不能为null
   * @param parentPath 父路径，不能为null
   * @return 上传结果DTO
   * @throws DefaultSysException 当文件上传失败时抛出
   */
  public static UploadDto upload(MultipartFile file, String parentPath) {

    return upload(file, parentPath, false);
  }

  /**
   * 上传文件（指定父路径和安全设置）
   * 使用指定的父路径和安全设置上传文件
   *
   * @param file 上传文件，不能为null
   * @param parentPath 父路径，不能为null
   * @param security 是否启用安全模式
   * @return 上传结果DTO
   * @throws DefaultSysException 当文件上传失败时抛出
   */
  public static UploadDto upload(MultipartFile file, String parentPath, Boolean security) {

    return upload(file, Collections.singletonList(parentPath), security);
  }

  /**
   * 上传文件（指定父路径列表和安全设置）
   * 使用指定的父路径列表和安全设置上传文件
   *
   * @param file 上传文件，不能为null
   * @param parentPathList 父路径列表，不能为null
   * @param security 是否启用安全模式
   * @return 上传结果DTO
   * @throws DefaultSysException 当文件上传失败时抛出
   */
  public static UploadDto upload(MultipartFile file, List<String> parentPathList,
      Boolean security) {

    Assert.notNull(file);
    try (InputStream is = file.getInputStream()) {
      return upload(is, file.getOriginalFilename(), parentPathList, security);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }

  /**
   * 上传文件
   *
   * @param file
   * @return
   */
  public static UploadDto upload(File file) {

    return upload(file, Collections.emptyList(), false);
  }

  /**
   * 上传文件
   *
   * @param file
   * @return
   */
  public static UploadDto upload(File file, String parentPath) {

    return upload(file, parentPath, false);
  }

  /**
   * 上传文件
   *
   * @param file
   * @return
   */
  public static UploadDto upload(File file, String parentPath, Boolean security) {

    return upload(file, Collections.singletonList(parentPath), security);
  }

  /**
   * 上传文件
   *
   * @param file
   * @return
   */
  public static UploadDto upload(File file, List<String> parentPathList, Boolean security) {

    Assert.notNull(file);
    try (InputStream is = FileUtil.getInputStream(file)) {
      return upload(is, file.getName(), parentPathList, security);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }

  /**
   * 上传文件
   *
   * @return
   */
  public static UploadDto upload(InputStream is, String fileName, List<String> parentPathList,
      Boolean security) {

    Assert.notNull(is);
    SysConfService sysConfService = ApplicationUtil.getBean(SysConfService.class);
    String key = security ? "security-upload.type" : "upload.type";
    String uploadTypeStr = sysConfService.findByKey(key, "LOCAL");

    String suffix = FileUtil.getSuffix(fileName);
    String randomFileName = IdUtil.getUUID();
    String fullFileName =
        randomFileName + (StringUtil.isEmpty(suffix) ? StringPool.EMPTY_STR : ("." + suffix));

    if (security) {
      SecurityUploadHandler uploadHandler = UploadHandlerFactory.getSecurityInstance(uploadTypeStr);

      return uploadHandler.upload(is, getDefaultLocations(parentPathList), fullFileName);
    } else {
      UploadHandler uploadHandler = UploadHandlerFactory.getInstance(uploadTypeStr);

      return uploadHandler.upload(is, getDefaultLocations(parentPathList), fullFileName);
    }
  }

  public static String generatePresignedUrl(String uploadType, String objectName) {

    SysConfService sysConfService = ApplicationUtil.getBean(SysConfService.class);
    SecurityUploadHandler uploadHandler = UploadHandlerFactory.getSecurityInstance(uploadType);
    Long expire = sysConfService.getLong("security-upload.sign-url-expired", 600L);

    return uploadHandler.generatePresignedUrl(objectName, expire);
  }

  /**
   * 获取默认的locations
   *
   * @return
   */
  private static List<String> getDefaultLocations(List<String> parentPathList) {

    LocalDate now = LocalDate.now();
    List<String> locations = new ArrayList<>();
    if(TenantUtil.enableTenant()) {
      locations.add(TenantContextHolder.getTenantIdStr());
    }
    if (!CollectionUtil.isEmpty(parentPathList)) {
      locations.addAll(parentPathList.stream().filter(t -> !StringUtil.isBlank(t)).collect(
          Collectors.toList()));
    }
    locations.add(DateUtil.formatDate(now, "yyyy"));
    locations.add(DateUtil.formatDate(now, "MM"));
    locations.add(DateUtil.formatDate(now, "dd"));

    return locations;
  }
}
