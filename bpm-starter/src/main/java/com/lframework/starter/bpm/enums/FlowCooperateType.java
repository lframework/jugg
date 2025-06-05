package com.lframework.starter.bpm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum FlowCooperateType implements BaseEnum<Integer> {
  NORMAL(0, "或签"),
  ALL(1, "会签"),
  VOTE(2, "票签")
  ;

  @EnumValue
  private final Integer code;

  private final String desc;

  FlowCooperateType(Integer code, String desc) {

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
