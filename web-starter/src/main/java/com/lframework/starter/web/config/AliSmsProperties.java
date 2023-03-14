package com.lframework.starter.web.config;

import lombok.Data;

@Data
public class AliSmsProperties {

  private String endpoint;

  private String accessKeyId;

  private String accessKeySecret;
}
