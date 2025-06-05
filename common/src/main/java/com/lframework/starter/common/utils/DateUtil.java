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
 *
 * @author zmj
 */
public class DateUtil extends cn.hutool.core.date.LocalDateTimeUtil {

  public static String formatDate(LocalDate localDate) {

    return LocalDateTimeUtil.formatNormal(localDate);
  }

  public static String formatDate(LocalDate localDate, String format) {

    return LocalDateTimeUtil.format(localDate, format);
  }

  public static LocalTime toLocalTime(Date date) {
    if (date == null) {
      return null;
    }
    return LocalDateTimeUtil.of(date).toLocalTime();
  }

  public static LocalDate toLocalDate(Date date) {
    if (date == null) {
      return null;
    }
    return LocalDateTimeUtil.of(date).toLocalDate();
  }

  public static LocalDateTime toLocalDateTime(Date date) {
    return LocalDateTimeUtil.of(date);
  }

  public static Date toDate(LocalDateTime dateTime) {

    if (dateTime == null) {
      return null;
    }

    return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static Date toDate(LocalDate date) {

    if (date == null) {
      return null;
    }

    return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  public static LocalDateTime toLocalDateTime(LocalDate localDate) {

    return LocalDateTime.of(localDate, LocalTime.MIN);
  }

  public static LocalDateTime toLocalDateTimeMax(LocalDate localDate) {

    return LocalDateTime.of(localDate, LocalTime.MAX.withNano(0));
  }

  public static String formatDateTime(LocalDateTime localDateTime) {

    return formatDateTime(localDateTime, StringPool.DATE_TIME_PATTERN);
  }

  public static String formatDateTime(LocalDateTime localDateTime, String pattern) {

    return format(localDateTime, pattern);
  }

  public static long getTime(LocalDateTime localDateTime) {

    return Timestamp.valueOf(localDateTime).getTime();
  }

  public static long getTime(LocalDate localDate) {

    return Timestamp.valueOf(toLocalDateTime(localDate)).getTime();
  }

  public static long getTime(Date date) {

    return date.getTime();
  }

  /**
   * 获取本周日期
   *
   * @return
   */
  public static List<LocalDate> getCurrentWeekDates() {

    return getWeekDates(LocalDate.now());
  }

  /**
   * 获取{date}当周日期
   *
   * @return
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
   * 获取本月日期
   *
   * @return
   */
  public static List<LocalDate> getCurrentMonthDates() {
    LocalDate now = LocalDate.now();

    return getMonthDates(LocalDate.now());
  }

  /**
   * 获取{date}当前日期
   *
   * @return
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
   * 获取本季度日期
   *
   * @return
   */
  public static List<LocalDate> getCurrentQuarterDates() {

    return getQuarterDates(LocalDate.now());
  }

  /**
   * 获取{date}当前季度日期
   *
   * @return
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
   * 获取当前半年日期
   *
   * @return
   */
  public static List<LocalDate> getCurrentHalfYearDates(LocalDate date) {
    return getHalfYearDates(LocalDate.now());
  }

  /**
   * 获取{date}当前半年日期
   *
   * @return
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
   * 获取当前年日期
   *
   * @return
   */
  public static List<LocalDate> getCurrentYearDates() {
    return getYearDates(LocalDate.now());
  }

  /**
   * 获取{date}当前年日期
   *
   * @return
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
   * 获取{date}属于第几季度
   *
   * @param date
   * @return
   */
  public static int getQuarter(LocalDate date) {
    return (date.getMonthValue() - 1) / 3 + 1;
  }
}
