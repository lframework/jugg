package com.lframework.starter.web.core.utils;

import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;

@Slf4j
public class CronUtil {

  /**
   * 根据时间获取Cron
   *
   * @param dateTime
   * @return
   */
  public static String getDateTimeCron(LocalDateTime dateTime) {

    return dateTime.getSecond() + " " + dateTime.getMinute() + " " + dateTime.getHour() + " "
        + dateTime.getDayOfMonth() + " " + dateTime.getMonthValue() + " ? " + dateTime.getYear();
  }

  /**
   * 根据日期获取Cron
   *
   * @param date
   * @return
   */
  public static String getDateCron(LocalDate date) {

    return "0 0 0 " + date.getDayOfMonth() + " " + date.getMonthValue() + " ? " + date.getYear();
  }

  /**
   * 获取最大时间的日期Cron
   *
   * @param date
   * @return
   */
  public static String getDateMaxCron(LocalDate date) {

    return "59 59 23 " + date.getDayOfMonth() + " " + date.getMonthValue() + " ? " + date.getYear();
  }

  public static Boolean match(String cron, LocalDateTime dateTime) {
    if (StringUtil.isBlank(cron) || dateTime == null) {
      return false;

    }

    CronExpression exp = null;
    try {
      exp = new CronExpression(cron);
    } catch (ParseException e) {
      log.error(e.getMessage(), e);
      return false;
    }

    return exp.isSatisfiedBy(DateUtil.toDate(dateTime));
  }
}
