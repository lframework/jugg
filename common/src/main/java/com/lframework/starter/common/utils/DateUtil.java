package com.lframework.starter.common.utils;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.lframework.starter.common.constants.StringPool;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 * 基于HuTool的LocalDateTimeUtil进行扩展，提供时间操作相关的工具方法
 * 包括时间格式化、类型转换、时间范围计算等功能
 *
 * @author lframework@163.com
 */
public class DateUtil extends cn.hutool.core.date.LocalDateTimeUtil {

  /**
   * 格式化日期为标准格式
   * 使用默认的日期格式（yyyy-MM-dd）格式化LocalDate
   *
   * @param localDate 要格式化的日期，不能为null
   * @return 格式化后的日期字符串
   */
  public static String formatDate(LocalDate localDate) {

    return LocalDateTimeUtil.formatNormal(localDate);
  }

  /**
   * 格式化日期为指定格式
   * 使用指定的日期格式格式化LocalDate
   *
   * @param localDate 要格式化的日期，不能为null
   * @param format 日期格式，不能为null或空
   * @return 格式化后的日期字符串
   */
  public static String formatDate(LocalDate localDate, String format) {

    return LocalDateTimeUtil.format(localDate, format);
  }

  /**
   * 将Date转换为LocalTime
   * 提取Date中的时间部分转换为LocalTime
   *
   * @param date 要转换的Date对象，可以为null
   * @return 转换后的LocalTime对象，如果输入为null则返回null
   */
  public static LocalTime toLocalTime(Date date) {
    if (date == null) {
      return null;
    }
    return LocalDateTimeUtil.of(date).toLocalTime();
  }

  /**
   * 将Date转换为LocalDate
   * 提取Date中的日期部分转换为LocalDate
   *
   * @param date 要转换的Date对象，可以为null
   * @return 转换后的LocalDate对象，如果输入为null则返回null
   */
  public static LocalDate toLocalDate(Date date) {
    if (date == null) {
      return null;
    }
    return LocalDateTimeUtil.of(date).toLocalDate();
  }

  /**
   * 将Date转换为LocalDateTime
   * 将Date对象转换为LocalDateTime对象
   *
   * @param date 要转换的Date对象，不能为null
   * @return 转换后的LocalDateTime对象
   */
  public static LocalDateTime toLocalDateTime(Date date) {
    return LocalDateTimeUtil.of(date);
  }

  /**
   * 将LocalDateTime转换为Date
   * 将LocalDateTime对象转换为Date对象
   *
   * @param dateTime 要转换的LocalDateTime对象，可以为null
   * @return 转换后的Date对象，如果输入为null则返回null
   */
  public static Date toDate(LocalDateTime dateTime) {

    if (dateTime == null) {
      return null;
    }

    return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  /**
   * 将LocalDate转换为Date
   * 将LocalDate对象转换为Date对象，时间部分为00:00:00
   *
   * @param date 要转换的LocalDate对象，可以为null
   * @return 转换后的Date对象，如果输入为null则返回null
   */
  public static Date toDate(LocalDate date) {

    if (date == null) {
      return null;
    }

    return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  /**
   * 将LocalDate转换为LocalDateTime（最小时间）
   * 将LocalDate转换为当天的00:00:00时间
   *
   * @param localDate 要转换的LocalDate对象，不能为null
   * @return 转换后的LocalDateTime对象
   */
  public static LocalDateTime toLocalDateTime(LocalDate localDate) {

    return LocalDateTime.of(localDate, LocalTime.MIN);
  }

  /**
   * 将LocalDate转换为LocalDateTime（最大时间）
   * 将LocalDate转换为当天的23:59:59.999999999时间
   *
   * @param localDate 要转换的LocalDate对象，不能为null
   * @return 转换后的LocalDateTime对象
   */
  public static LocalDateTime toLocalDateTimeMax(LocalDate localDate) {

    return LocalDateTime.of(localDate, LocalTime.MAX.withNano(0));
  }

  /**
   * 格式化日期时间为标准格式
   * 使用默认的日期时间格式（yyyy-MM-dd HH:mm:ss）格式化LocalDateTime
   *
   * @param localDateTime 要格式化的日期时间，不能为null
   * @return 格式化后的日期时间字符串
   */
  public static String formatDateTime(LocalDateTime localDateTime) {

    return formatDateTime(localDateTime, StringPool.DATE_TIME_PATTERN);
  }

  /**
   * 格式化日期时间为指定格式
   * 使用指定的日期时间格式格式化LocalDateTime
   *
   * @param localDateTime 要格式化的日期时间，不能为null
   * @param pattern 日期时间格式，不能为null或空
   * @return 格式化后的日期时间字符串
   */
  public static String formatDateTime(LocalDateTime localDateTime, String pattern) {

    return format(localDateTime, pattern);
  }

  /**
   * 获取LocalDateTime的时间戳
   * 将LocalDateTime转换为毫秒时间戳
   *
   * @param localDateTime 要转换的LocalDateTime对象，不能为null
   * @return 毫秒时间戳
   */
  public static long getTime(LocalDateTime localDateTime) {

    return Timestamp.valueOf(localDateTime).getTime();
  }

  /**
   * 获取LocalDate的时间戳
   * 将LocalDate转换为毫秒时间戳（当天00:00:00）
   *
   * @param localDate 要转换的LocalDate对象，不能为null
   * @return 毫秒时间戳
   */
  public static long getTime(LocalDate localDate) {

    return Timestamp.valueOf(toLocalDateTime(localDate)).getTime();
  }

  /**
   * 获取Date的时间戳
   * 获取Date对象的毫秒时间戳
   *
   * @param date 要获取时间戳的Date对象，不能为null
   * @return 毫秒时间戳
   */
  public static long getTime(Date date) {

    return date.getTime();
  }

  /**
   * 获取本周的所有日期
   * 返回当前周从周一到周日的所有日期
   *
   * @return 本周的日期列表，包含7个日期
   */
  public static List<LocalDate> getCurrentWeekDates() {

    return getWeekDates(LocalDate.now());
  }

  /**
   * 获取指定日期所在周的所有日期
   * 返回指定日期所在周从周一到周日的所有日期
   *
   * @param date 指定日期，不能为null
   * @return 该周的日期列表，包含7个日期
   */
  public static List<LocalDate> getWeekDates(LocalDate date) {
    LocalDate firstDate = date.with(WeekFields.ISO.dayOfWeek(), 1L);

    List<LocalDate> results = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      results.add(firstDate.plusDays(i));
    }

    return results;
  }


  /**
   * 获取本月的所有日期
   * 返回当前月份的所有日期
   *
   * @return 本月的日期列表
   */
  public static List<LocalDate> getCurrentMonthDates() {
    LocalDate now = LocalDate.now();

    return getMonthDates(LocalDate.now());
  }

  /**
   * 获取指定日期所在月份的所有日期
   * 返回指定日期所在月份的所有日期
   *
   * @param date 指定日期，不能为null
   * @return 该月的日期列表
   */
  public static List<LocalDate> getMonthDates(LocalDate date) {
    LocalDate firstDate = date.withDayOfMonth(1);
    List<LocalDate> results = new ArrayList<>();
    for (int i = 0; i < 31; i++) {
      LocalDate tmp = firstDate.plusDays(i);
      if (tmp.getMonthValue() != firstDate.getMonthValue()) {
        break;
      }
      results.add(tmp);
    }

    return results;
  }

  /**
   * 获取本季度的所有日期
   * 返回当前季度的所有日期
   *
   * @return 本季度的日期列表
   */
  public static List<LocalDate> getCurrentQuarterDates() {

    return getQuarterDates(LocalDate.now());
  }

  /**
   * 获取指定日期所在季度的所有日期
   * 返回指定日期所在季度的所有日期
   *
   * @param date 指定日期，不能为null
   * @return 该季度的日期列表
   */
  public static List<LocalDate> getQuarterDates(LocalDate date) {
    int quarter = getQuarter(date);

    LocalDate firstDate = date.withMonth((quarter - 1) * 3 + 1).withDayOfMonth(1);

    List<LocalDate> results = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      results.addAll(getMonthDates(firstDate.plusMonths(i)));
    }

    return results;
  }

  /**
   * 获取当前半年的所有日期
   * 返回当前半年的所有日期
   *
   * @param date 指定日期，不能为null
   * @return 当前半年的日期列表
   */
  public static List<LocalDate> getCurrentHalfYearDates(LocalDate date) {
    return getHalfYearDates(LocalDate.now());
  }

  /**
   * 获取指定日期所在半年的所有日期
   * 返回指定日期所在半年的所有日期
   *
   * @param date 指定日期，不能为null
   * @return 该半年的日期列表
   */
  public static List<LocalDate> getHalfYearDates(LocalDate date) {
    LocalDate firstDate = date.getMonthValue() > 6 ? date.withMonth(7).withDayOfMonth(1)
        : date.withMonth(1).withDayOfMonth(1);
    LocalDate lastDate = date.getMonthValue() > 6 ? date.withMonth(12).withDayOfMonth(31)
        : date.withMonth(6).withDayOfMonth(30);

    List<LocalDate> results = new ArrayList<>();
    int i = 0;
    while (true) {

      LocalDate tmp = firstDate.plusDays(i);
      if (tmp.isAfter(lastDate)) {
        break;
      }
      results.add(tmp);
      i++;
    }

    return results;
  }

  /**
   * 获取当前年的所有日期
   * 返回当前年份的所有日期
   *
   * @return 当前年的日期列表
   */
  public static List<LocalDate> getCurrentYearDates() {
    return getYearDates(LocalDate.now());
  }

  /**
   * 获取指定日期所在年份的所有日期
   * 返回指定日期所在年份的所有日期
   *
   * @param date 指定日期，不能为null
   * @return 该年的日期列表
   */
  public static List<LocalDate> getYearDates(LocalDate date) {
    LocalDate firstDate = date.withMonth(1).withDayOfMonth(1);
    LocalDate lastDate = date.withMonth(12).withDayOfMonth(31);

    List<LocalDate> results = new ArrayList<>();
    int i = 0;
    while (true) {

      LocalDate tmp = firstDate.plusDays(i);
      if (tmp.isAfter(lastDate)) {
        break;
      }
      results.add(tmp);
      i++;
    }

    return results;
  }

  /**
   * 获取指定日期属于第几季度
   * 根据月份计算季度：1-3月为第1季度，4-6月为第2季度，7-9月为第3季度，10-12月为第4季度
   *
   * @param date 指定日期，不能为null
   * @return 季度数（1-4）
   */
  public static int getQuarter(LocalDate date) {
    return (date.getMonthValue() - 1) / 3 + 1;
  }
}
