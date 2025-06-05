package com.lframework.starter.bpm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum FlowSkipType implements BaseEnum<Integer> {
  START(0, "发起"),
  APPROVE_PASS(1, "通过"),
  APPROVE_REFUSE(2, "退回"),
  UNDO(3, "撤回"),
  REJECT(4, "反对"),
  TERMINATION(5, "终止");

  @EnumValue
  private final Integer code;

  private final String desc;

  FlowSkipType(Integer code, String desc) {

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
