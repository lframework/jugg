package com.lframework.starter.web.inner.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum SysMenuComponentType implements BaseEnum<Integer> {

  NORMAL(0, "普通"), CUSTOM_LIST(1, "自定义列表"), CUSTOM_PAGE(3, "自定义页面");

  @EnumValue
  private final Integer code;

  private final String desc;

  SysMenuComponentType(Integer code, String desc) {

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
