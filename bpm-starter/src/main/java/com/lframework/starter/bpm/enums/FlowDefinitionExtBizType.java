package com.lframework.starter.bpm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum FlowDefinitionExtBizType implements BaseEnum<String> {
  SYSTEM("system", "系统内部功能");

  @EnumValue
  private final String code;

  private final String desc;

  FlowDefinitionExtBizType(String code, String desc) {

    this.code = code;
    this.desc = desc;
  }

  @Override
  public String getCode() {

    return this.code;
  }

  @Override
  public String getDesc() {

    return this.desc;
  }
}
