package com.lframework.starter.web.inner.components.permission;

import com.lframework.starter.web.core.components.permission.SysDataPermissionDataPermissionType;

/**
 * 商品数据权限
 */
public final class ProductDataPermissionDataPermissionType implements
    SysDataPermissionDataPermissionType {

  @Override
  public Integer getCode() {
    return 1;
  }
}
