package com.lframework.starter.web.components.upload.handler;

import java.io.InputStream;
import java.util.List;

public interface UploadHandler {

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
  String upload(InputStream is, List<String> locations, String fileName);
}
