package com.lframework.starter.web.core.utils;

import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;

/**
 * Cron表达式工具类
 * 提供Cron表达式生成和匹配功能，支持时间到Cron表达式的转换
 * 包括日期时间Cron生成、Cron表达式匹配验证等功能
 *
 * @author lframework@163.com
 */
@Slf4j
public class CronUtil {

  /**
   * 根据日期时间生成Cron表达式
   * 生成指定日期时间的一次性执行Cron表达式
   *
   * @param dateTime 指定的日期时间，不能为null
   * @return Cron表达式字符串
   */
  public static String getDateTimeCron(LocalDateTime dateTime) {

    return dateTime.getSecond() + " " + dateTime.getMinute() + " " + dateTime.getHour() + " "
        + dateTime.getDayOfMonth() + " " + dateTime.getMonthValue() + " ? " + dateTime.getYear();
  }

  /**
   * 根据日期生成Cron表达式（当天00:00:00）
   * 生成指定日期当天凌晨执行的Cron表达式
   *
   * @param date 指定的日期，不能为null
   * @return Cron表达式字符串
   */
  public static String getDateCron(LocalDate date) {

    return "0 0 0 " + date.getDayOfMonth() + " " + date.getMonthValue() + " ? " + date.getYear();
  }

  /**
   * 根据日期生成Cron表达式（当天23:59:59）
   * 生成指定日期当天最后一秒执行的Cron表达式
   *
   * @param date 指定的日期，不能为null
   * @return Cron表达式字符串
   */
  public static String getDateMaxCron(LocalDate date) {

    return "59 59 23 " + date.getDayOfMonth() + " " + date.getMonthValue() + " ? " + date.getYear();
  }

  /**
   * 验证Cron表达式是否匹配指定时间
   * 检查给定的Cron表达式是否在指定时间执行
   *
   * @param cron Cron表达式，不能为null或空
   * @param dateTime 要验证的日期时间，不能为null
   * @return true-匹配，false-不匹配或Cron表达式无效
   */
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
