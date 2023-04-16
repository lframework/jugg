package com.lframework.starter.mybatis.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum SysDataPermissionDataPermissionType implements BaseEnum<Integer> {
  PRODUCT(1, "商品"), ORDER(2, "单据");

  @EnumValue
  private final Integer code;

  private final String desc;

  SysDataPermissionDataPermissionType(Integer code, String desc) {

    this.code = code;
    this.desc = desc;
  }

  @Override
  public Integer getCode() {

    return this.code;
  }

  @Override
  public String getDesc() {

    return this.desc;
  }
}
