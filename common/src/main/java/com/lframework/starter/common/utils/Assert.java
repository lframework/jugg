package com.lframework.starter.common.utils;

/**
 * 断言工具类
 * 基于HuTool的Assert进行扩展，提供参数验证和断言相关的工具方法
 * 包括参数非空验证、数值范围验证、条件断言等功能
 *
 * @author lframework@163.com
 */
public class Assert extends cn.hutool.core.lang.Assert {

  /**
   * 断言数字大于0
   * 验证数字必须大于0，否则抛出异常
   *
   * @param number 要验证的数字，不能为null
   * @throws IllegalArgumentException 当数字为null或小于等于0时抛出
   */
  public static void greaterThanZero(Number number) {

    notNull(number);

    isTrue(number.doubleValue() > 0D);
  }

  /**
   * 断言数字大于或等于0
   * 验证数字必须大于或等于0，否则抛出异常
   *
   * @param number 要验证的数字，不能为null
   * @throws IllegalArgumentException 当数字为null或小于0时抛出
   */
  public static void greaterThanOrEqualToZero(Number number) {

    notNull(number);

    isTrue(number.doubleValue() >= 0D);
  }
}
