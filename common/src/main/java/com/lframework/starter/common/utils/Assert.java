package com.lframework.starter.common.utils;

/**
 * 断言工具类
 *
 * @author zmj
 */
public class Assert extends cn.hutool.core.lang.Assert {

  public static void greaterThanZero(Number number) {

    notNull(number);

    isTrue(number.doubleValue() > 0D);
  }

  public static void greaterThanOrEqualToZero(Number number) {

    notNull(number);

    isTrue(number.doubleValue() >= 0D);
  }
}
