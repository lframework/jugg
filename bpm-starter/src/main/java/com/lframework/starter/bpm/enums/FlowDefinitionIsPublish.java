package com.lframework.starter.bpm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum FlowDefinitionIsPublish implements BaseEnum<Integer> {
  Y(1, "已发布"), N(0, "未发布"), UNABLE(9, "失效");

  @EnumValue
  private final Integer code;

  private final String desc;

  FlowDefinitionIsPublish(Integer code, String desc) {

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
