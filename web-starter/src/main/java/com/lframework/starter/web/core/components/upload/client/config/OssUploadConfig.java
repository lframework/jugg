package com.lframework.starter.web.core.components.upload.client.config;

import java.io.Serializable;
import lombok.Data;

@Data
public class OssUploadConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  private String customUrl;

  private String endpoint;

  private String internalEndPoint;

  private String accessKeyId;

  private String accessKeySecret;

  private String bucketName;
}
