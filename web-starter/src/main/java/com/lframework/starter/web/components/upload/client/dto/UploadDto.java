package com.lframework.starter.web.components.upload.client.dto;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

@Data
public class UploadDto implements BaseDto {

  private static final long serialVersionUID = 1L;

  private String url;

  private String objectName;

  private String uploadType;
}
