package com.lframework.starter.web.core.annotations.security;

import com.lframework.starter.web.core.components.security.PermissionCalcType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasPermission {

  /**
   * 权限
   *
   * @return
   */
  String[] value();

  /**
   * 计算方式
   *
   * @return
   */
  PermissionCalcType calcType() default PermissionCalcType.OR;
}
