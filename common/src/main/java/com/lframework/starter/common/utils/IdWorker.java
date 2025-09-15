package com.lframework.starter.common.utils;

import cn.hutool.core.lang.Snowflake;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * 分布式ID生成器
 * 基于HuTool的Snowflake雪花算法实现，生成全局唯一的分布式ID
 * 支持高并发场景下的ID生成，保证ID的唯一性和有序性
 *
 * @author lframework@163.com
 */
@Slf4j
public class IdWorker extends Snowflake {

  /**
   * 默认构造函数
   * 使用默认的workerId和dataCenterId创建ID生成器
   */
  public IdWorker() {
    super();
  }

  /**
   * 指定workerId的构造函数
   *
   * @param workerId 工作机器ID，范围0-31
   */
  public IdWorker(long workerId) {
    super(workerId);
  }

  /**
   * 指定workerId和dataCenterId的构造函数
   *
   * @param workerId 工作机器ID，范围0-31
   * @param dataCenterId 数据中心ID，范围0-31
   */
  public IdWorker(long workerId, long dataCenterId) {
    super(workerId, dataCenterId);
  }

  /**
   * 指定workerId、dataCenterId和时钟使用方式的构造函数
   *
   * @param workerId 工作机器ID，范围0-31
   * @param dataCenterId 数据中心ID，范围0-31
   * @param isUseSystemClock 是否使用系统时钟
   */
  public IdWorker(long workerId, long dataCenterId, boolean isUseSystemClock) {
    super(workerId, dataCenterId, isUseSystemClock);
  }

  /**
   * 指定纪元时间、workerId、dataCenterId和时钟使用方式的构造函数
   *
   * @param epochDate 纪元时间，不能为null
   * @param workerId 工作机器ID，范围0-31
   * @param dataCenterId 数据中心ID，范围0-31
   * @param isUseSystemClock 是否使用系统时钟
   */
  public IdWorker(Date epochDate, long workerId, long dataCenterId,
      boolean isUseSystemClock) {
    super(epochDate, workerId, dataCenterId, isUseSystemClock);
  }

  /**
   * 完整参数的构造函数
   *
   * @param epochDate 纪元时间，不能为null
   * @param workerId 工作机器ID，范围0-31
   * @param dataCenterId 数据中心ID，范围0-31
   * @param isUseSystemClock 是否使用系统时钟
   * @param timeOffset 时间偏移量
   */
  public IdWorker(Date epochDate, long workerId, long dataCenterId,
      boolean isUseSystemClock, long timeOffset) {
    super(epochDate, workerId, dataCenterId, isUseSystemClock, timeOffset);
  }
}
