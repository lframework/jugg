package com.lframework.starter.bpm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum FlowDefinitionActivityStatus implements BaseEnum<Integer> {
  ACTIVATE(1, "已激活"), DEACTIVATE(0, "已挂起");

  @EnumValue
  private final Integer code;

  private final String desc;

  FlowDefinitionActivityStatus(Integer code, String desc) {

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
