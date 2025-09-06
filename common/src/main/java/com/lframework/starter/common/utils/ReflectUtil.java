package com.lframework.starter.common.utils;

import com.lframework.starter.common.functions.SFunction;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * 基于HuTool的ReflectUtil进行扩展，提供反射操作相关的工具方法
 * 包括字段获取、方法调用、Lambda表达式字段名提取等功能
 *
 * @author lframework@163.com
 */
public class ReflectUtil extends cn.hutool.core.util.ReflectUtil {

  /**
   * 获取实体类的字段名称（实体声明的字段名称）
   * 通过Lambda表达式获取对应的字段名称，支持getter方法
   *
   * @param fn Lambda表达式，如 User::getName
   * @param <T> 实体类型
   * @return 字段名称，如 "name"
   * @throws RuntimeException 当获取字段名失败时抛出
   */
  public static <T> String getFieldName(SFunction<T, ?> fn) {

    SerializedLambda serializedLambda = getSerializedLambda(fn);

    // 从lambda信息取出method、field、class等
    String fieldName = serializedLambda.getImplMethodName().substring("get".length());
    fieldName = fieldName.replaceFirst(fieldName.charAt(0) + "",
        (fieldName.charAt(0) + "").toLowerCase());

    // 从field取出字段名，可以根据实际情况调整
    return fieldName.replaceAll("[A-Z]", "$0");
  }

  /**
   * 获取Lambda表达式的序列化信息
   * 通过反射获取Lambda表达式的序列化Lambda对象
   *
   * @param fn Lambda表达式，不能为null
   * @param <T> 实体类型
   * @return 序列化Lambda对象
   * @throws RuntimeException 当获取序列化Lambda失败时抛出
   */
  private static <T> SerializedLambda getSerializedLambda(SFunction<T, ?> fn) {
    // 从function取出序列化方法
    Method writeReplaceMethod;
    try {
      writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }

    // 从序列化方法取出序列化的lambda信息
    boolean isAccessible = writeReplaceMethod.isAccessible();
    writeReplaceMethod.setAccessible(true);
    SerializedLambda serializedLambda;
    try {
      serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    writeReplaceMethod.setAccessible(isAccessible);
    return serializedLambda;
  }
}
