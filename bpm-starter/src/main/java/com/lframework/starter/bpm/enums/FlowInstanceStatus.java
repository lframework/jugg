package com.lframework.starter.bpm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum FlowInstanceStatus implements BaseEnum<String> {
  CREATE("0", "待提交"),
  APPROVING("1", "审核中"),
  APPROVE_PASS("2", "审核通过"),
  TERMINATION("4", "已终止"),
  CANCEL("5", "已作废"),
  UNDO("6", "已撤回"),
  FINISH("8", "已完成"),
  REFUSE("9", "已退回"),
  INVALID("10", "已失效"),
  REVOKE("11", "已驳回");

  @EnumValue
  private final String code;

  private final String desc;

  FlowInstanceStatus(String code, String desc) {

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
