package com.lframework.starter.web.core.utils;

/**
 * 多租户工具类
 * 提供多租户相关的工具方法，支持租户功能开关控制
 * 包括租户启用状态检查等功能
 *
 * @author lframework@163.com
 */
public class TenantUtil {

  /**
   * 检查是否启用多租户功能
   * 从配置中获取多租户功能的启用状态
   *
   * @return true-已启用多租户，false-未启用多租户
   */
  public static boolean enableTenant() {
    return true;
  }
}
