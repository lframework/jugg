package com.lframework.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenQueryType implements BaseEnum<Integer> {
  EQ(0, "="),
  GT(1, ">"),
  GE(2, ">="),
  LT(3, "<"),
  LE(4, "<="),
  NE(5, "!="),
  IN(6, "IN"),
  NOT_IN(7, "NOT IN"),
  LEFT_LIKE(8, "LIKE %?"),
  RIGHT_LIKE(9, "LIKE ?%"),
  AROUND_LIKE(10, "LIKE %?%");

  @EnumValue
  private final Integer code;

  private final String desc;

  GenQueryType(Integer code, String desc) {

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
