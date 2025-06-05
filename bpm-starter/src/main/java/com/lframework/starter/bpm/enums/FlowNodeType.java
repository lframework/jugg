package com.lframework.starter.bpm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum FlowNodeType implements BaseEnum<Integer> {
  START(0, "开始节点"),
  TASK(1, "中间节点"),
  END(2, "结束节点"),
  EXCLUSIVE_GATEWAY(3, "互斥网关"),
  PARALLEL_GATEWAY(4, "并行网关");


  @EnumValue
  private final Integer code;

  private final String desc;

  FlowNodeType(Integer code, String desc) {

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
