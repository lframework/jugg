package com.lframework.starter.web.core.utils;

import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.enums.BaseEnum;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * 枚举工具类
 * 提供枚举操作相关的工具方法，支持BaseEnum接口的枚举处理
 * 包括枚举值查找、描述获取、枚举列表获取等功能
 *
 * @author lframework@163.com
 */
@Slf4j
public class EnumUtil {

  private static final Map<Class<? extends BaseEnum<? extends Serializable>>, List<? extends BaseEnum<? extends Serializable>>> ENUM_POOL;

  static {
    ENUM_POOL = new ConcurrentHashMap<>();
  }

  /**
   * 根据代码获取枚举描述
   * 通过枚举代码查找对应的描述信息
   *
   * @param clazz 枚举类，不能为null
   * @param code 枚举代码，不能为null
   * @param <C> 枚举类型
   * @return 枚举描述，如果未找到则返回null
   */
  public static <C extends BaseEnum<? extends Serializable>> String getDesc(Class<C> clazz,
      Serializable code) {

    List<C> enumList = getEnumList(clazz);

    for (C c : enumList) {
      if (c.getCode().equals(code)) {
        return c.getDesc();
      }
    }

    return null;
  }

  /**
   * 根据代码获取枚举实例
   * 通过枚举代码查找对应的枚举实例
   *
   * @param clazz 枚举类，不能为null
   * @param code 枚举代码，可以为null
   * @param <C> 枚举类型
   * @return 枚举实例，如果未找到则返回null
   */
  public static <C extends BaseEnum<? extends Serializable>> C getByCode(Class<C> clazz,
      Serializable code) {

    if (ObjectUtil.isNull(code)) {
      return null;
    }

    List<C> enumList = getEnumList(clazz);
    for (C c : enumList) {
      if (c.getCode() == null) {
        continue;
      }
      if (String.valueOf(c.getCode()).equals(String.valueOf(code))) {
        return c;
      }
    }

    return null;
  }

  /**
   * 根据描述获取枚举实例
   * 通过枚举描述查找对应的枚举实例
   *
   * @param clazz 枚举类，不能为null
   * @param desc 枚举描述，不能为null或空
   * @param <C> 枚举类型
   * @return 枚举实例，如果未找到则返回null
   */
  public static <C extends BaseEnum<? extends Serializable>> C getByDesc(Class<C> clazz,
      String desc) {

    if (StringUtil.isNullOrUndefined(desc)) {
      return null;
    }

    List<C> enumList = getEnumList(clazz);
    for (C c : enumList) {
      if (c.getDesc().equals(desc)) {
        return c;
      }
    }

    return null;
  }

  /**
   * 获取枚举的所有描述列表
   * 返回指定枚举类的所有描述信息
   *
   * @param clazz 枚举类，不能为null
   * @param <C> 枚举类型
   * @return 描述列表
   */
  public static <C extends BaseEnum<? extends Serializable>> List<String> getDescs(Class<C> clazz) {
    List<C> enumList = getEnumList(clazz);
    return enumList.stream().map(t -> t.getDesc()).collect(Collectors.toList());
  }

  /**
   * 获取枚举列表
   * 从缓存或反射获取枚举实例列表
   *
   * @param clazz 枚举类，不能为null
   * @param <C> 枚举类型
   * @return 枚举实例列表
   * @throws DefaultSysException 当获取枚举失败时抛出
   */
  private static <C extends BaseEnum<? extends Serializable>> List<C> getEnumList(Class<C> clazz) {

    if (!ENUM_POOL.containsKey(clazz)) {
      try {
        List<C> enumList = new ArrayList<>();
        if (Enum.class.isAssignableFrom(clazz)) {
          // 如果是Enum类型
          Method method = clazz.getMethod("values");

          C[] enums = (C[]) method.invoke(clazz);

          if (ArrayUtil.isNotEmpty(enums)) {
            enumList.addAll(Arrays.asList(enums));
          }
        } else {
          // 如果不是Enum类型，那么必须是Bean
          Map<String, C> enums = ApplicationUtil.getBeansOfType(clazz);
          if (!CollectionUtil.isEmpty(enums)) {
            enumList.addAll(enums.values());
          }
        }
        ENUM_POOL.put(clazz, enumList);
      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        log.error(e.getMessage(), e);
        throw new DefaultSysException(e.getMessage());
      }
    }

    List<C> enumList = (List<C>) ENUM_POOL.get(clazz);

    return enumList;
  }
}
