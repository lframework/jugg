package com.lframework.starter.web.core.components.upload.client.config;

import java.io.Serializable;
import lombok.Data;

@Data
public class CosUploadConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  private String customUrl;

  private String endpoint;

  private String region;

  private String secretId;

  private String secretKey;

  private String bucketName;
}
