package com.lframework.starter.web.components.upload.client.config;

import java.io.Serializable;
import lombok.Data;

@Data
public class LocalUploadConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  private String domain;

  private String url;

  private String location;
}
