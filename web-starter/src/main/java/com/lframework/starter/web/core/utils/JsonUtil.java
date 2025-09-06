package com.lframework.starter.web.core.utils;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.StringUtil;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * JSON工具类
 * 基于HuTool的JSONUtil进行扩展，提供JSON序列化和反序列化功能
 * 包括对象转JSON、JSON转对象、类型转换等功能
 *
 * @author lframework@163.com
 */
@Slf4j
public class JsonUtil extends JSONUtil {

  private static final ObjectMapper OBJECT_MAPPER = ApplicationUtil.getBean(ObjectMapper.class);

  /**
   * 将对象转换为JSON字符串
   * 使用Jackson ObjectMapper进行序列化
   *
   * @param obj 要转换的对象，可以为null
   * @return JSON字符串，如果对象为null则返回null
   * @throws DefaultSysException 当序列化失败时抛出
   */
  public static String toJsonString(Object obj) {

    if (obj == null) {
      return null;
    }
    try {
      return OBJECT_MAPPER.writer().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }

  /**
   * 将JSON字符串转换为对象（使用TypeReference）
   * 支持复杂泛型类型的反序列化
   *
   * @param jsonStr JSON字符串，不能为null或空
   * @param typeRef 类型引用，不能为null
   * @param <T> 目标类型
   * @return 转换后的对象，如果JSON为空则返回null
   * @throws DefaultSysException 当反序列化失败时抛出
   */
  public static <T> T parseObject(String jsonStr, TypeReference<T> typeRef) {
    if (StringUtil.isEmpty(jsonStr) || typeRef == null) {
      return null;
    }

    try {
      return OBJECT_MAPPER.readValue(jsonStr, typeRef);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }

  /**
   * 将JSON字符串转换为对象（使用Class）
   * 支持简单类型的反序列化
   *
   * @param jsonStr JSON字符串，不能为null或空
   * @param clazz 目标类型，不能为null
   * @param <T> 目标类型
   * @return 转换后的对象，如果JSON为空则返回null
   * @throws DefaultSysException 当反序列化失败时抛出
   */
  public static <T> T parseObject(String jsonStr, Class<T> clazz) {

    if (StringUtil.isEmpty(jsonStr) || clazz == null) {
      return null;
    }

    try {
      return OBJECT_MAPPER.readValue(jsonStr, clazz);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }

  /**
   * 将JSON字符串转换为Map
   * 支持指定键值类型的Map反序列化
   *
   * @param jsonStr JSON字符串，不能为null或空
   * @param keyClazz 键类型，不能为null
   * @param valueClazz 值类型，不能为null
   * @param <K> 键类型
   * @param <V> 值类型
   * @return 转换后的Map，如果JSON为空则返回null
   * @throws DefaultSysException 当反序列化失败时抛出
   */
  public static <K, V> Map<K, V> parseMap(String jsonStr, Class<K> keyClazz, Class<V> valueClazz) {
    if (StringUtil.isEmpty(jsonStr) || keyClazz == null || valueClazz == null) {
      return null;
    }

    try {
      return OBJECT_MAPPER.readValue(jsonStr,
          OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, keyClazz, valueClazz));
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }

  /**
   * 将JSON字符串转换为List
   * 支持指定元素类型的List反序列化
   *
   * @param jsonStr JSON字符串，不能为null或空
   * @param clazz 元素类型，不能为null
   * @param <T> 元素类型
   * @return 转换后的List，如果JSON为空则返回null
   * @throws DefaultSysException 当反序列化失败时抛出
   */
  public static <T> List<T> parseList(String jsonStr, Class<T> clazz) {

    if (StringUtil.isEmpty(jsonStr) || clazz == null) {
      return null;
    }

    try {
      return OBJECT_MAPPER.readValue(jsonStr,
          OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }

  /**
   * 对象类型转换
   * 使用Jackson进行对象类型转换
   *
   * @param obj 要转换的对象，不能为null
   * @param clazz 目标类型，不能为null
   * @param <T> 目标类型
   * @return 转换后的对象，如果对象为null则返回null
   */
  public static <T> T convert(Object obj, Class<T> clazz) {

    if (obj == null || clazz == null) {
      return null;
    }

    return OBJECT_MAPPER.convertValue(obj, clazz);
  }

  /**
   * 判断字符串是否为JSON对象
   * 验证字符串是否为有效的JSON对象格式
   *
   * @param jsonStr 要验证的字符串，不能为null
   * @return true-是JSON对象，false-不是JSON对象或格式无效
   */
  public static boolean isJsonObject(String jsonStr) {

    if (StringUtil.isBlank(jsonStr)) {
      return false;
    }

    try {
      JsonNode jsonNode = OBJECT_MAPPER.readTree(jsonStr);
      return jsonNode.getNodeType() != JsonNodeType.ARRAY;
    } catch (JsonProcessingException e) {
      return false;
    }
  }

  /**
   * 判断字符串是否为JSON数组
   * 验证字符串是否为有效的JSON数组格式
   *
   * @param jsonStr 要验证的字符串，不能为null
   * @return true-是JSON数组，false-不是JSON数组或格式无效
   */
  public static boolean isJsonArray(String jsonStr) {

    if (StringUtil.isBlank(jsonStr)) {
      return false;
    }

    try {
      JsonNode jsonNode = OBJECT_MAPPER.readTree(jsonStr);
      return jsonNode.getNodeType() == JsonNodeType.ARRAY;
    } catch (JsonProcessingException e) {
      return false;
    }
  }

  /**
   * 判断字符串是否为有效JSON
   * 验证字符串是否为有效的JSON格式
   *
   * @param jsonStr 要验证的字符串，不能为null
   * @return true-是有效JSON，false-不是有效JSON
   */
  public static boolean isJson(String jsonStr) {

    if (StringUtil.isBlank(jsonStr)) {
      return false;
    }

    try {
      OBJECT_MAPPER.readTree(jsonStr);
    } catch (JsonProcessingException e) {
      return false;
    }

    return true;
  }
}
