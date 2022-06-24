package com.lframework.common.utils;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.lframework.common.constants.StringPool;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author zmj
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {

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
}
