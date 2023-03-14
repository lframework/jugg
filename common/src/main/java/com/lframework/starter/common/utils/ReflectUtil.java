package com.lframework.starter.common.utils;

import com.lframework.starter.common.functions.SFunction;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author zmj
 */
public class ReflectUtil extends cn.hutool.core.util.ReflectUtil {

  /**
   * 获取实体类的字段名称(实体声明的字段名称)
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
