package com.lframework.starter.web.core.utils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.env.Environment;

/**
 * Spring应用上下文工具类
 * 提供Spring应用上下文访问功能，支持Bean获取、属性获取、事件发布等
 * 包括Bean管理、配置属性访问、应用事件发布等功能
 *
 * @author lframework@163.com
 */
public class ApplicationUtil {

  private static volatile ApplicationContext APPLICATION_CONTEXT;

  public static void setApplicationContext(ApplicationContext applicationContext) {

    APPLICATION_CONTEXT = applicationContext;
  }

  private static ApplicationContext requireApplicationContext() {
    if (APPLICATION_CONTEXT == null) {
      throw new IllegalStateException("Spring ApplicationContext has not been initialized yet");
    }
    return APPLICATION_CONTEXT;
  }

  /**
   * 安全获取Bean（按名称）
   * 获取指定名称的Bean，如果不存在则返回null
   *
   * @param beanName Bean名称，不能为null
   * @return Bean实例，如果不存在则返回null
   */
  public static Object safeGetBean(String beanName) {

    try {
      ApplicationContext applicationContext = APPLICATION_CONTEXT;
      return applicationContext == null ? null : applicationContext.getBean(beanName);
    } catch (NoSuchBeanDefinitionException e) {
      return null;
    }
  }

  /**
   * 安全获取Bean（按类型）
   * 获取指定类型的Bean，如果不存在则返回null
   *
   * @param clazz Bean类型，不能为null
   * @param <T> Bean类型
   * @return Bean实例，如果不存在则返回null
   */
  public static <T> T safeGetBean(Class<T> clazz) {

    try {
      ApplicationContext applicationContext = APPLICATION_CONTEXT;
      return applicationContext == null ? null : applicationContext.getBean(clazz);
    } catch (NoSuchBeanDefinitionException e) {
      return null;
    }
  }

  /**
   * 获取Bean（按名称）
   * 获取指定名称的Bean，如果不存在则抛出异常
   *
   * @param beanName Bean名称，不能为null
   * @return Bean实例
   * @throws NoSuchBeanDefinitionException 当Bean不存在时抛出
   */
  public static Object getBean(String beanName) {

    return requireApplicationContext().getBean(beanName);
  }

  /**
   * 获取Bean（按类型）
   * 获取指定类型的Bean，如果不存在则抛出异常
   *
   * @param clazz Bean类型，不能为null
   * @param <T> Bean类型
   * @return Bean实例
   * @throws NoSuchBeanDefinitionException 当Bean不存在时抛出
   */
  public static <T> T getBean(Class<T> clazz) {

    return requireApplicationContext().getBean(clazz);
  }

  /**
   * 获取指定类型的所有Bean
   * 获取指定类型的所有Bean实例，以Map形式返回
   *
   * @param clazz Bean类型，不能为null
   * @param <T> Bean类型
   * @return Bean名称到实例的映射，如果获取失败则返回空Map
   */
  public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {

    try {
      return requireApplicationContext().getBeansOfType(clazz);
    } catch (BeansException e) {
      return Collections.emptyMap();
    }
  }

  /**
   * 发布应用事件
   * 发布Spring应用事件，触发相关监听器
   *
   * @param event 要发布的事件，不能为null
   */
  public static void publishEvent(ApplicationEvent event) {

    requireApplicationContext().publishEvent(event);
  }

  /**
   * 获取配置属性
   * 获取指定键的配置属性值
   *
   * @param key 属性键，不能为null
   * @return 属性值，如果不存在则返回null
   */
  public static String getProperty(String key) {

    return requireApplicationContext().getEnvironment().getProperty(key);
  }

  /**
   * 获取必需的配置属性
   * 获取指定键的配置属性值，如果不存在则抛出异常
   *
   * @param key 属性键，不能为null
   * @return 属性值
   * @throws IllegalStateException 当属性不存在时抛出
   */
  public static String getRequiredProperty(String key) {

    return requireApplicationContext().getEnvironment().getRequiredProperty(key);
  }

  /**
   * 解析占位符
   * 解析字符串中的占位符为实际值
   *
   * @param s 包含占位符的字符串，不能为null
   * @return 解析后的字符串
   */
  public static String resolvePlaceholders(String s) {

    return requireApplicationContext().getEnvironment().resolvePlaceholders(s);
  }

  /**
   * 验证对象
   * 使用JSR-303验证注解验证对象
   *
   * @param obj 要验证的对象，不能为null
   * @throws ConstraintViolationException 当验证失败时抛出
   */
  public static void validate(Object obj) {
    Validator validator = getBean(Validator.class);
    Set<ConstraintViolation<Object>> errors = validator.validate(obj);
    if (errors != null && errors.size() > 0) {
      throw new ConstraintViolationException("参数校验失败！", errors);
    }
  }

  public static Environment getEnv() {
    return requireApplicationContext().getEnvironment();
  }
}
