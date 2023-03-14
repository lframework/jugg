package com.lframework.starter.common.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;

public class AliSmsUtil {

  /**
   * 使用AK&SK初始化账号Client
   *
   * @param accessKeyId
   * @param accessKeySecret
   * @return Client
   * @throws Exception
   */
  public static Client createClient(String accessKeyId, String accessKeySecret, String endpoint) throws Exception {

    Config config = new Config().setAccessKeyId(accessKeyId).setAccessKeySecret(accessKeySecret);
    config.endpoint = endpoint;
    return new Client(config);
  }
}