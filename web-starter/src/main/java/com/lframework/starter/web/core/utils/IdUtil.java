package com.lframework.starter.web.core.utils;

import com.lframework.starter.common.utils.IdWorker;

/**
 * ID工具类
 *
 * @author zmj
 */
public class IdUtil {

  /**
   * 获取ID（雪花算法）
   *
   * @return
   */
  public static String getId() {

    IdWorker idWorker = ApplicationUtil.getBean(IdWorker.class);
    return idWorker.nextIdStr();
  }

  public static long getIdLong() {
    IdWorker idWorker = ApplicationUtil.getBean(IdWorker.class);
    return idWorker.nextId();
  }

  /**
   * 获取UUID
   *
   * @return
   */
  public static String getUUID() {
    return cn.hutool.core.util.IdUtil.fastSimpleUUID();
  }
}
