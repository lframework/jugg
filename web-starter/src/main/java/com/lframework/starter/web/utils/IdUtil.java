package com.lframework.starter.web.utils;

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

  /**
   * 获取UUID
   *
   * @return
   */
  public static String getUUID() {
    return cn.hutool.core.util.IdUtil.fastSimpleUUID();
  }
}
