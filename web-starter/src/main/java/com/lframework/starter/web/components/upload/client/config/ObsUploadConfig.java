package com.lframework.starter.web.components.upload.client.config;

import java.io.Serializable;
import lombok.Data;

@Data
public class ObsUploadConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  private String customUrl;

  private String endpoint;

  private String ak;

  private String sk;

  private String bucketName;
}
