package com.lframework.starter.common.utils;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数字工具类
 * 提供精确的数字计算、比较、精度控制等功能，避免浮点数计算精度问题
 * 支持加减乘除运算、大小比较、税率计算、精度验证等常用数字操作
 *
 * @author lframework@163.com
 */
public class NumberUtil {

  /**
   * 判断数字的小数位数是否符合指定精度要求
   * 用于验证数字的小数位数是否在允许范围内
   *
   * @param value 待验证的数字，不能为null
   * @param precision 允许的最大小数位数，必须大于等于0
   * @return true-符合精度要求，false-不符合精度要求或参数无效
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
   * 判断数字是否为整数
   * 检查Number对象是否表示一个整数值（没有小数部分）
   *
   * @param number 待检查的数字，不能为null
   * @return true-是整数，false-不是整数或参数为null
   */
  public static boolean isInteger(Number number) {
    if (number == null) {
      return false;
    }

    if (number instanceof Integer || number instanceof Long || number instanceof Short
        || number instanceof Byte) {
      return true;
    }

    BigDecimal bigDecimal = getNumber(number);
    return bigDecimal.compareTo(new BigDecimal(bigDecimal.toBigInteger())) == 0;
  }

  /**
   * 精确加法运算
   * 对多个数字进行精确的加法计算，避免浮点数精度问题
   *
   * @param numbers 参与加法运算的数字，支持可变参数
   * @return 所有数字相加的结果，使用BigDecimal保证精度
   */
  public static BigDecimal add(Number... numbers) {

    BigDecimal result = new BigDecimal(0);

    for (Number number : numbers) {
      result = result.add(getNumber(number));
    }

    return result;
  }

  /**
   * 精确减法运算
   * 从被减数中依次减去所有减数，进行精确的减法计算
   *
   * @param n1 被减数，不能为null
   * @param numbers 减数数组，不能为空
   * @return 减法运算的结果，使用BigDecimal保证精度
   * @throws IllegalArgumentException 当被减数为null或减数数组为空时抛出
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
   * 精确乘法运算
   * 对多个数字进行精确的乘法计算，避免浮点数精度问题
   *
   * @param numbers 参与乘法运算的数字，支持可变参数
   * @return 所有数字相乘的结果，使用BigDecimal保证精度
   */
  public static BigDecimal mul(Number... numbers) {

    BigDecimal result = new BigDecimal(1);
    for (Number number : numbers) {
      result = result.multiply(getNumber(number));
    }

    return result;
  }

  /**
   * 精确除法运算（四舍五入）
   * 使用四舍五入模式进行精确的除法计算
   *
   * @param n1 被除数，不能为null
   * @param numbers 除数数组，不能为空且不能包含0
   * @return 除法运算的结果，使用BigDecimal保证精度
   * @throws IllegalArgumentException 当被除数为null或除数数组为空时抛出
   * @throws DefaultSysException 当除数为0时抛出
   */
  public static BigDecimal div(Number n1, Number... numbers) {

    return div(RoundingMode.HALF_UP, n1, numbers);
  }

  /**
   * 精确除法运算（指定舍入模式）
   * 使用指定的舍入模式进行精确的除法计算
   *
   * @param mode 小数位处理方式，不能为null
   * @param n1 被除数，不能为null
   * @param numbers 除数数组，不能为空且不能包含0
   * @return 除法运算的结果，使用BigDecimal保证精度
   * @throws IllegalArgumentException 当被除数为null或除数数组为空时抛出
   * @throws DefaultSysException 当除数为0时抛出
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
   * 判断第一个数字是否大于第二个数字
   * 使用BigDecimal进行精确比较，避免浮点数比较问题
   *
   * @param n1 第一个数字，不能为null
   * @param n2 第二个数字，不能为null
   * @return true-第一个数字大于第二个数字，false-否则
   */
  public static boolean gt(Number n1, Number n2) {

    return getNumber(n1).compareTo(getNumber(n2)) > 0;
  }

  /**
   * 判断第一个数字是否小于第二个数字
   * 使用BigDecimal进行精确比较，避免浮点数比较问题
   *
   * @param n1 第一个数字，不能为null
   * @param n2 第二个数字，不能为null
   * @return true-第一个数字小于第二个数字，false-否则
   */
  public static boolean lt(Number n1, Number n2) {

    return getNumber(n1).compareTo(getNumber(n2)) < 0;
  }

  /**
   * 判断第一个数字是否大于或等于第二个数字
   * 使用BigDecimal进行精确比较，避免浮点数比较问题
   *
   * @param n1 第一个数字，不能为null
   * @param n2 第二个数字，不能为null
   * @return true-第一个数字大于或等于第二个数字，false-否则
   */
  public static boolean ge(Number n1, Number n2) {

    return getNumber(n1).compareTo(getNumber(n2)) >= 0;
  }

  /**
   * 判断第一个数字是否小于或等于第二个数字
   * 使用BigDecimal进行精确比较，避免浮点数比较问题
   *
   * @param n1 第一个数字，不能为null
   * @param n2 第二个数字，不能为null
   * @return true-第一个数字小于或等于第二个数字，false-否则
   */
  public static boolean le(Number n1, Number n2) {

    return getNumber(n1).compareTo(getNumber(n2)) <= 0;
  }

  /**
   * 判断两个数字是否相等
   * 使用BigDecimal进行精确比较，避免浮点数比较问题
   *
   * @param n1 第一个数字，不能为null
   * @param n2 第二个数字，不能为null
   * @return true-两个数字相等，false-否则
   */
  public static boolean equal(Number n1, Number n2) {

    return getNumber(n1).compareTo(getNumber(n2)) == 0;
  }

  /**
   * 根据无税价格计算含税价格
   * 计算公式：含税价格 = 无税价格 × (1 + 税率/100)
   *
   * @param unTaxPrice 无税价格，不能为null
   * @param taxRate 税率（百分比），不能为null，如13表示13%
   * @return 计算后的含税价格，使用BigDecimal保证精度
   */
  public static BigDecimal calcTaxPrice(Number unTaxPrice, Number taxRate) {

    return mul(unTaxPrice, add(div(taxRate, 100), BigDecimal.ONE));
  }

  /**
   * 根据含税价格计算无税价格
   * 计算公式：无税价格 = 含税价格 ÷ (1 + 税率/100)
   *
   * @param taxPrice 含税价格，不能为null
   * @param taxRate 税率（百分比），不能为null，如13表示13%
   * @return 计算后的无税价格，使用BigDecimal保证精度
   */
  public static BigDecimal calcUnTaxPrice(Number taxPrice, Number taxRate) {

    return div(taxPrice, add(div(taxRate, 100), BigDecimal.ONE));
  }

  /**
   * 根据含税价格和无税价格计算税率
   * 计算公式：税率 = (含税价格 ÷ 无税价格 - 1) × 100
   *
   * @param tax 含税价格或含税金额，不能为null
   * @param unTax 无税价格或无税金额，不能为null且不能为0
   * @return 计算后的税率（百分比），使用BigDecimal保证精度
   * @throws DefaultSysException 当无税价格为0时抛出
   */
  public static BigDecimal calcTaxRate(Number tax, Number unTax) {
    return mul(sub(div(tax, unTax), BigDecimal.ONE), 100);
  }

  /**
   * 获取多个数字中的最小值
   * 使用BigDecimal进行精确比较，避免浮点数比较问题
   *
   * @param numbers 参与比较的数字，不能为null且至少包含一个元素
   * @return 所有数字中的最小值，使用BigDecimal保证精度
   * @throws IllegalArgumentException 当数字数组为null或为空时抛出
   */
  public static BigDecimal min(Number... numbers) {
    Assert.notEmpty(numbers);

    BigDecimal min = getNumber(numbers[0]);
    for (int i = 1; i < numbers.length; i++) {
      BigDecimal current = getNumber(numbers[i]);
      if (current.compareTo(min) < 0) {
        min = current;
      }
    }

    return min;
  }

  /**
   * 获取多个数字中的最大值
   * 使用BigDecimal进行精确比较，避免浮点数比较问题
   *
   * @param numbers 参与比较的数字，不能为null且至少包含一个元素
   * @return 所有数字中的最大值，使用BigDecimal保证精度
   * @throws IllegalArgumentException 当数字数组为null或为空时抛出
   */
  public static BigDecimal max(Number... numbers) {
    Assert.notEmpty(numbers);

    BigDecimal max = getNumber(numbers[0]);
    for (int i = 1; i < numbers.length; i++) {
      BigDecimal current = getNumber(numbers[i]);
      if (current.compareTo(max) > 0) {
        max = current;
      }
    }

    return max;
  }

  /**
   * 获取数字的绝对值
   * 使用BigDecimal进行精确计算，避免浮点数精度问题
   *
   * @param number 待计算绝对值的数字，不能为null
   * @return 数字的绝对值，使用BigDecimal保证精度
   * @throws IllegalArgumentException 当数字为null时抛出
   */
  public static BigDecimal abs(Number number) {
    Assert.notNull(number);

    if (number instanceof BigDecimal) {
      return ((BigDecimal) number).abs();
    } else {
      return BigDecimal.valueOf(number.doubleValue()).abs();
    }
  }

  /**
   * 将数字格式化为指定精度的小数
   * 使用四舍五入模式保留指定的小数位数
   *
   * @param number 待格式化的数字，不能为null
   * @param precision 保留的小数位数，必须大于等于0
   * @return 格式化后的数字，使用BigDecimal保证精度
   */
  public static BigDecimal getNumber(Number number, int precision) {

    precision = Math.max(0, precision);

    BigDecimal result = getNumber(number).setScale(precision, BigDecimal.ROUND_HALF_UP);

    return result.stripTrailingZeros();
  }

  /**
   * 将Number类型转换为BigDecimal类型
   * 内部工具方法，用于统一数字类型转换
   *
   * @param number 待转换的数字，不能为null
   * @return 转换后的BigDecimal对象
   * @throws IllegalArgumentException 当数字为null时抛出
   */
  private static BigDecimal getNumber(Number number) {

    Assert.notNull(number);

    if (number instanceof BigDecimal) {
      return (BigDecimal) number;
    } else {
      return BigDecimal.valueOf(number.doubleValue());
    }
  }
}
