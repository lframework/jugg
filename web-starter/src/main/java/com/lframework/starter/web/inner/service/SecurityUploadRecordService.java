package com.lframework.starter.web.inner.service;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.SecurityUploadRecord;

/**
 * 安全上传记录 Service
 */
public interface SecurityUploadRecordService extends BaseMpService<SecurityUploadRecord> {

  /**
   * 新增
   *
   * @param uploadType
   * @param filePath
   * @return
   */
  String create(String uploadType, String filePath);
}
