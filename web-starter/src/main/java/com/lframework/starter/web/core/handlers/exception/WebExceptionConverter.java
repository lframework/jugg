package com.lframework.starter.web.core.handlers.exception;

import com.lframework.starter.common.exceptions.BaseException;

public interface WebExceptionConverter {

  /**
   * 最高优先级，用于处理没有子类的异常
   */
  int FIRST_ORDER = Integer.MIN_VALUE;

  /**
   * 次高优先级，用于处理系统内定义的异常，如：ClientException、SysException。
   */
  int SECOND_ORDER = FIRST_ORDER + 100;

  /**
   * 第三优先级，用于处理系统之外的框架定义的并且需要特定处理的异常，如：BindException。
   */
  int THIRD_ORDER = SECOND_ORDER + 100;
  /**
   * 最低优先级，用于处理范围最大的异常，如：Exception。
   * <p>
   * 当本层都没有处理的异常，会统一处理，如：Throwable。
   */
  int LAST_ORDER = Integer.MAX_VALUE;

  /**
   * 是否匹配
   *
   * @param e
   * @return
   */
  boolean isMatch(Throwable e);

  /**
   * 转换异常
   *
   * @param e
   * @return
   */
  BaseException convert(Throwable e);

  /**
   * 优先级 值越小越靠前
   *
   * @return
   */
  default int getOrder() {
    return FIRST_ORDER;
  }
}
