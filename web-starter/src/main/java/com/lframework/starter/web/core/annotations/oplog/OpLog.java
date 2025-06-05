package com.lframework.starter.web.core.annotations.oplog;

import com.lframework.starter.web.core.components.oplog.OpLogType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统日志注解
 *
 * @author zmj
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpLog {

  /**
   * 日志类型 用于做业务区分
   *
   * @return
   */
  Class<? extends OpLogType> type();

  /**
   * 日志名称 需要填充参数部分用{}占位，会根据params的值进行填充
   *
   * @return
   */
  String name() default "";

  /**
   * 需要保存的参数 Spel表达式
   *
   * @return
   */
  String[] params() default {};

  /**
   * 是否循环填充日志名称 会将params中的collection循环format
   *
   * @return
   */
  boolean loopFormat() default false;

  /**
   * 是否自动保存参数
   *
   * @return
   */
  boolean autoSaveParams() default false;
}
