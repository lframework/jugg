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
   * <p>
   * 当requirePlatform == true时并且value是空时，此时只校验平台权限；如果value不为空，那么同时校验权限。
   * <p>
   * 当requirePlatform == false时，只校验权限，如果此时value为空，那么校验权限一定不通过。
   *
   * @return
   */
  String[] value() default {};

  /**
   * 计算方式
   *
   * @return
   */
  PermissionCalcType calcType() default PermissionCalcType.OR;

  /**
   * 是否需要平台权限
   *
   * @return
   */
  boolean requirePlatform() default false;
}
