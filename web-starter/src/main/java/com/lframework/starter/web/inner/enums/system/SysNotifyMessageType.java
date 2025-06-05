package com.lframework.starter.web.inner.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum SysNotifyMessageType implements BaseEnum<Integer> {

  SYS(0, "站内信"), EMAIL(1, "邮件");

  @EnumValue
  private final Integer code;

  private final String desc;

  SysNotifyMessageType(Integer code, String desc) {

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
