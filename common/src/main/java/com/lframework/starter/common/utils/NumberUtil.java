package com.lframework.starter.common.utils;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数字工具类
 *
 * @author zmj
 */
public class NumberUtil {

  /**
   * 判断 value 是否为 precision 位小数
   *
   * @param value
   * @param precision
   * @return
   */
  public static boolean isNumberPrecision(Number value, int precision) {

    if (value == null) {
      return false;
    }

    if (precision < 0) {
      return false;
    }

    String str = BigDecimal.valueOf(value.doubleValue()).toPlainString();

    if (str.contains(StringPool.DECIMAL_POINT)) {
      while (StringPool.ZERO.equals(str.substring(str.length() - 1))) {
        // 将数字末尾为0的字符去除
        str = str.substring(0, str.length() - 1);
      }

      if (StringPool.DECIMAL_POINT.equals(str.substring(str.length() - 1))) {
        return true;
      }

      return str.substring(str.indexOf(StringPool.DECIMAL_POINT)).length() - 1 <= precision;
    }

    return true;
  }

  /**
   * 加法
   *
   * @param numbers
   * @return
   */
  public static BigDecimal add(Number... numbers) {

    BigDecimal result = new BigDecimal(0);

    for (Number number : numbers) {
      result = result.add(getNumber(number));
    }

    return result;
  }

  /**
   * 减法
   *
   * @param n1      被减数
   * @param numbers 减数
   * @return
   */
  public static BigDecimal sub(Number n1, Number... numbers) {

    Assert.notNull(n1);
    Assert.notEmpty(numbers);

    BigDecimal result = getNumber(n1);

    for (Number number : numbers) {
      BigDecimal tmp = getNumber(number);

      result = result.subtract(tmp);
    }

    return result;
  }

  /**
   * 乘法
   *
   * @param numbers
   * @return
   */
  public static BigDecimal mul(Number... numbers) {

    BigDecimal result = new BigDecimal(1);
    for (Number number : numbers) {
      result = result.multiply(getNumber(number));
    }

    return result;
  }

  /**
   * 除法
   *
   * @param n1      被除数
   * @param numbers 除数
   * @return
   */
  public static BigDecimal div(Number n1, Number... numbers) {

    return div(RoundingMode.HALF_UP, n1, numbers);
  }

  /**
   * 除法
   *
   * @param mode    小数位处理方式
   * @param n1      被除数
   * @param numbers 除数
   * @return
   */
  public static BigDecimal div(RoundingMode mode, Number n1, Number... numbers) {

    Assert.notNull(n1);
    Assert.notEmpty(numbers);

    BigDecimal result = getNumber(n1);

    for (Number number : numbers) {
      BigDecimal tmp = getNumber(number);
      if (equal(tmp, 0)) {
        throw new DefaultSysException("除数不能等于0");
      }

      result = new BigDecimal(result.divide(tmp, 16, mode).stripTrailingZeros().toPlainString());
    }

    return result;
  }

  /**
   * 判断n1是否大于n2
   *
   * @param n1
   * @param n2
   * @return
   */
  public static boolean gt(Number n1, Number n2) {

    return getNumber(n1).compareTo(getNumber(n2)) > 0;
  }

  /**
   * 判断n1是否小于n2
   *
   * @param n1
   * @param n2
   * @return
   */
  public static boolean lt(Number n1, Number n2) {

    return getNumber(n1).compareTo(getNumber(n2)) < 0;
  }

  /**
   * 判断n1是否大于或等于n2
   *
   * @param n1
   * @param n2
   * @return
   */
  public static boolean ge(Number n1, Number n2) {

    return getNumber(n1).compareTo(getNumber(n2)) >= 0;
  }

  /**
   * 判断n1是否小于或等于n2
   *
   * @param n1
   * @param n2
   * @return
   */
  public static boolean le(Number n1, Number n2) {

    return getNumber(n1).compareTo(getNumber(n2)) <= 0;
  }

  /**
   * 判断n1是否等于n2
   *
   * @param n1
   * @param n2
   * @return
   */
  public static boolean equal(Number n1, Number n2) {

    return getNumber(n1).compareTo(getNumber(n2)) == 0;
  }

  /**
   * 根据无税价格计算含税价格
   *
   * @param unTaxPrice
   * @param taxRate    税率（%）
   * @return
   */
  public static BigDecimal calcTaxPrice(Number unTaxPrice, Number taxRate) {

    return mul(unTaxPrice, add(div(taxRate, 100), BigDecimal.ONE));
  }

  /**
   * 根据含税价格计算无税价格
   *
   * @param taxPrice
   * @param taxRate  税率（%）
   * @return
   */
  public static BigDecimal calcUnTaxPrice(Number taxPrice, Number taxRate) {

    return div(taxPrice, add(div(taxRate, 100), BigDecimal.ONE));
  }

  /**
   * 保留{precision}位小数
   *
   * @param number
   * @param precision
   * @return
   */
  public static BigDecimal getNumber(Number number, int precision) {

    precision = Math.max(0, precision);

    BigDecimal result = getNumber(number).setScale(precision, BigDecimal.ROUND_HALF_UP);

    return result;
  }

  private static BigDecimal getNumber(Number number) {

    Assert.notNull(number);

    if (number instanceof BigDecimal) {
      return (BigDecimal) number;
    } else {
      return BigDecimal.valueOf(number.doubleValue());
    }
  }
}
