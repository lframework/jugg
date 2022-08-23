package com.lframework.starter.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenComponent implements BaseEnum<Integer> {
  FRONT_VUE(1, "前端VUE文件"), FRONT_JS(2, "前端JS文件"), FRONT_CSS(3, "前端CSS文件"), BACK_CONTROLLER(1000,
      "后端Controller"), BACK_SERVICE(1001, "后端Service"), BACK_MAPPER(1002,
      "后端Mapper"), BACK_MAPPER_XML(1003,
      "后端Mapper XML"), BACK_ENTITY(1004, "后端实体类"), BACK_VO(1005, "后端Vo"), BACK_BO(1006, "后端Bo"),
  ;

  @EnumValue
  private final Integer code;

  private final String desc;

  GenComponent(Integer code, String desc) {

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
