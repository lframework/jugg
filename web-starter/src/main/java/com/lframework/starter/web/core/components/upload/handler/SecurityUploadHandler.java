package com.lframework.starter.web.core.components.upload.handler;

import com.lframework.starter.web.core.components.upload.client.dto.UploadDto;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.inner.service.SecurityUploadRecordService;
import java.io.InputStream;
import java.util.List;

public interface SecurityUploadHandler {

  /**
   * 获取上传类型
   *
   * @return
   */
  String getType();

  /**
   * 上传文件
   *
   * @param is
   * @param locations
   * @param fileName
   * @return
   */
  UploadDto upload(InputStream is, List<String> locations, String fileName);

  default UploadDto createSecurityRecord(UploadDto dto) {
    SecurityUploadRecordService securityUploadRecordService = ApplicationUtil.getBean(SecurityUploadRecordService.class);
    String id = securityUploadRecordService.create(dto.getUploadType(), dto.getObjectName());
    dto.setSecurityUploadRecordId(id);
    return dto;
  }

  /**
   * 获取预签名URL
   *
   * @param objectName 文件名（全目录）
   * @param expiration 过期时间，单位秒
   * @return
   */
  String generatePresignedUrl(String objectName, long expiration);
}
