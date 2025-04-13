package com.lframework.starter.web.components.upload.client;

import com.lframework.starter.web.components.upload.client.dto.UploadDto;
import java.io.InputStream;
import java.util.List;

public interface UploadClient {

  /**
   * 上传文件
   *
   * @param is        输入流
   * @param locations 路径 比如想把文件存在${baseLocation}/datas/2022/01/01目录处，此时locations = ['datas', '2022',
   *                  '01', '01']
   * @param fileName  文件名
   */
  UploadDto upload(InputStream is, List<String> locations, String fileName);

  /**
   * 获取预签名URL
   *
   * @param objectName 文件名（全目录）
   * @param expiration 过期时间，单位秒
   * @return
   */
  String generatePresignedUrl(String objectName, long expiration);
}
