package com.lframework.starter.web.core.utils;

import com.lframework.starter.common.utils.IdWorker;

/**
 * ID生成工具类
 * 提供各种ID生成功能，包括雪花算法ID和UUID生成
 * 支持分布式环境下的唯一ID生成
 *
 * @author lframework@163.com
 */
public class IdUtil {

  /**
   * 获取雪花算法ID（字符串格式）
   * 生成全局唯一的分布式ID
   *
   * @return 雪花算法ID字符串
   */
  public static String getId() {

    IdWorker idWorker = ApplicationUtil.getBean(IdWorker.class);
    return idWorker.nextIdStr();
  }

  /**
   * 获取雪花算法ID（长整型格式）
   * 生成全局唯一的分布式ID
   *
   * @return 雪花算法ID长整型值
   */
  public static long getIdLong() {
    IdWorker idWorker = ApplicationUtil.getBean(IdWorker.class);
    return idWorker.nextId();
  }

  /**
   * 获取UUID（字符串格式）
   * 生成标准UUID字符串，不包含连字符
   *
   * @return UUID字符串
   */
  public static String getUUID() {
    return cn.hutool.core.util.IdUtil.fastSimpleUUID();
  }
}
