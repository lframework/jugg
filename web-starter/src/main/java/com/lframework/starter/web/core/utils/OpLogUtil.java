package com.lframework.starter.web.core.utils;

import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.starter.web.core.components.security.AbstractUserDetails;
import com.lframework.starter.web.core.components.security.SecurityUtil;
import com.lframework.starter.web.core.components.threads.DefaultRunnable;
import com.lframework.starter.web.inner.service.OpLogsService;
import com.lframework.starter.web.inner.vo.oplogs.CreateOpLogsVo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;

/**
 * 操作日志工具类
 * 提供操作日志记录和管理功能，支持异步日志提交和变量管理
 * 包括日志初始化、变量设置、日志提交、线程池管理等功能
 *
 * @author lframework@163.com
 */
@Slf4j
public class OpLogUtil {

  private static final ThreadLocal<Map<String, Map<String, Object>>> VARIABLE_POOL = new InheritableThreadLocal<>();

  private static final ThreadLocal<List<String>> LOG_ID_POOL = new InheritableThreadLocal<>();

  private static final ThreadLocal<Map<String, Object>> EXTRA_POOL = new InheritableThreadLocal<>();

  private static final ThreadLocal<List<CreateOpLogsVo>> OP_LOG_POOL = new InheritableThreadLocal<>();

  private static final ExecutorService OP_LOG_EXECUTOR = ThreadUtil.newExecutorByBlockingCoefficient(
      0);

  /**
   * 初始化操作日志
   * 为指定日志ID初始化日志环境
   *
   * @param logId 日志ID，不能为null
   */
  public static void init(String logId) {

    initPool();

    LOG_ID_POOL.get().add(logId);

    VARIABLE_POOL.get().putIfAbsent(logId, new HashMap<>());
  }

  /**
   * 添加操作日志
   * 将操作日志添加到当前线程的日志池中
   *
   * @param list 操作日志列表，不能为null
   */
  public static void addLogs(Collection<CreateOpLogsVo> list) {

    try {
      OP_LOG_POOL.get().addAll(list);
    } catch (Exception e) {
      // 这里异常不抛出
      log.error(e.getMessage(), e);
    }
  }

  /**
   * 设置日志变量
   * 为当前日志ID设置变量
   *
   * @param key 变量键，不能为null
   * @param value 变量值，可以为null
   */
  public static void setVariable(String key, Object value) {

    VARIABLE_POOL.get().get(getCurrentLogId()).put(key, value);
  }

  /**
   * 获取日志变量
   * 获取当前日志ID的所有变量
   *
   * @return 变量映射，如果不存在则返回null
   */
  public static Map<String, Object> getVariables() {

    return VARIABLE_POOL.get().get(getCurrentLogId());
  }

  /**
   * 获取额外信息
   * 获取当前日志ID的额外信息（JSON格式）
   *
   * @return 额外信息的JSON字符串，如果不存在则返回null
   */
  public static String getExtra() {

    Object value = EXTRA_POOL.get().get(getCurrentLogId());

    return value == null ? null : JsonUtil.toJsonString(value);
  }

  /**
   * 设置额外信息
   * 为当前日志ID设置额外信息
   *
   * @param value 额外信息对象，可以为null
   */
  public static void setExtra(Object value) {

    EXTRA_POOL.get().put(getCurrentLogId(), value);
  }

  /**
   * 提交操作日志（使用当前用户）
   * 异步提交当前线程的所有操作日志
   */
  public static void submitLog() {
    submitLog(SecurityUtil.getCurrentUser());
  }

  /**
   * 提交操作日志（指定用户）
   * 异步提交当前线程的所有操作日志
   *
   * @param currentUser 当前用户信息，可以为null
   */
  public static void submitLog(AbstractUserDetails currentUser) {
    if (LOG_ID_POOL.get() == null || LOG_ID_POOL.get().size() != 1) {
      return;
    }
    List<CreateOpLogsVo> logs = OP_LOG_POOL.get();
    OpLogsService opLogsService = ApplicationUtil.getBean(OpLogsService.class);
    OP_LOG_EXECUTOR.submit(new DefaultRunnable(() -> {
      if (SecurityUtil.getCurrentUser() != null) {
        opLogsService.create(logs);
      } else {
        if (currentUser != null) {
          try {
            SecurityUtil.setCurrentUser(currentUser);
            opLogsService.create(logs);
          } finally {
            SecurityUtil.removeCurrentUser();
          }
        }
      }
    }));
  }

  /**
   * 清理日志环境
   * 清理当前线程的日志相关数据
   */
  public static void clear() {
    boolean allClear = false;
    if (LOG_ID_POOL.get().size() == 1) {
      allClear = true;
    }

    VARIABLE_POOL.get().remove(getCurrentLogId());
    EXTRA_POOL.get().remove(getCurrentLogId());
    LOG_ID_POOL.get().remove(LOG_ID_POOL.get().size() - 1);

    if (allClear) {
      VARIABLE_POOL.remove();
      EXTRA_POOL.remove();
      LOG_ID_POOL.remove();
      OP_LOG_POOL.remove();
    }
  }

  /**
   * 初始化线程池
   * 初始化当前线程的日志相关线程池
   */
  private static void initPool() {

    if (VARIABLE_POOL.get() == null) {
      VARIABLE_POOL.set(new HashMap<>());
    }

    if (LOG_ID_POOL.get() == null) {
      LOG_ID_POOL.set(new ArrayList<>());
    }

    if (EXTRA_POOL.get() == null) {
      EXTRA_POOL.set(new HashMap<>());
    }

    if (OP_LOG_POOL.get() == null) {
      OP_LOG_POOL.set(new ArrayList<>());
    }
  }

  /**
   * 获取当前日志ID
   * 获取当前线程栈顶的日志ID
   *
   * @return 当前日志ID
   */
  private static String getCurrentLogId() {

    return LOG_ID_POOL.get().get(LOG_ID_POOL.get().size() - 1);
  }
}
