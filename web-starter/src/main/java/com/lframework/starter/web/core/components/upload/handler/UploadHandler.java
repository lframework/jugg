package com.lframework.starter.web.core.components.upload.handler;

import com.lframework.starter.web.core.components.upload.client.dto.UploadDto;
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
   * @return 下载链接
   */
  UploadDto upload(InputStream is, List<String> locations, String fileName);
}
