package com.lframework.starter.web.core.components.upload.client.dto;

import com.lframework.starter.web.core.dto.BaseDto;
import lombok.Data;

@Data
public class UploadDto implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * 安全上传记录ID
   *
   * 只有安全上传时有值
   */
  private String securityUploadRecordId;

  private String url;

  private String objectName;

  private String uploadType;
}
