package com.lframework.starter.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenDataType implements BaseEnum<Integer> {

  STRING(0, "String"), INTEGER(1, "Integer"), SHORT(2, "Short"), LONG(3, "Long"), DOUBLE(4,
      "Double"), LOCAL_DATE(5, "LocalDate"), LOCAL_DATE_TIME(6, "LocalDateTime"), LOCAL_TIME(7,
      "LocalTime"), BOOLEAN(8, "Boolean"), BIG_DECIMAL(9, "BigDecimal"),
  ;

  @EnumValue
  private final Integer code;

  private final String desc;

  GenDataType(Integer code, String desc) {

    this.code = code;
    this.desc = desc;
  }

  /**
   * 是否是数字类型
   *
   * @param type
   * @return
   */
  public static Boolean isNumberType(GenDataType type) {

    if (type == null) {
      return false;
    }

    return type == INTEGER || type == SHORT || type == LONG || type == DOUBLE
        || type == BIG_DECIMAL;
  }

  /**
   * 是否是小数类型
   *
   * @param type
   * @return
   */
  public static Boolean isDecimalType(GenDataType type) {
    if (type == null) {
      return false;
    }

    return type == DOUBLE || type == BIG_DECIMAL;
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
