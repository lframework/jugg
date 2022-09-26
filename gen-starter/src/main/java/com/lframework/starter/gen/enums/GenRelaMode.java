package com.lframework.starter.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenRelaMode implements BaseEnum<Integer> {
  LEFT_JOIN(0, "左连接"),
  RIGHT_JOIN(1, "右连接"),
  INNER_JOIN(2, "全连接");

  @EnumValue
  private final Integer code;

  private final String desc;

  GenRelaMode(Integer code, String desc) {
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
