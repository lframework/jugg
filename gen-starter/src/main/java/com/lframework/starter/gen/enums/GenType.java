package com.lframework.starter.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenType implements BaseEnum<Integer> {

  FRONT_QUERY(1, "前端查询"), FRONT_CREATE(2, "前端新增"), FRONT_UPDATE(3, "前端修改"), FRONT_DELETE(4,
      "前端删除"), BACK_QUERY(100,
      "后端查询"), BACK_CREATE(101, "后端新增"), BACK_UPDATE(102, "后端修改"), BACK_DELETE(103, "后端删除"),
  ;

  @EnumValue
  private final Integer code;

  private final String desc;

  GenType(Integer code, String desc) {

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
