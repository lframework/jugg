package com.lframework.starter.web.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum GenCustomListBtnType implements BaseEnum<Integer> {

  EXTERNAL(0, "外部链接"),
  ROUTE(1, "路由跳转"),
  EXCUTE_SCRIPT(3, "自定义表单");

  @EnumValue
  private final Integer code;

  private final String desc;

  GenCustomListBtnType(Integer code, String desc) {

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
