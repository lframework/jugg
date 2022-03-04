package com.lframework.starter.web.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CronUtil {

    /**
     * 根据时间获取Cron
     *
     * @param dateTime
     * @return
     */
    public static String getDateTimeCron(LocalDateTime dateTime) {
        return dateTime.getSecond() + " " + dateTime.getMinute() + " " + dateTime.getHour() + " " + dateTime.getDayOfMonth() + " " + dateTime.getMonthValue() + " ? " + dateTime.getYear();
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
}
