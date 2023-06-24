package com.lframework.starter.web.common.utils;

import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Spring Application 工具类
 *
 * @author zmj
 */
@Component
public class ApplicationUtil implements ApplicationContextAware {

  private static ApplicationContext APPLICATION_CONTEXT;

  public static Object safeGetBean(String beanName) {

    try {
      return APPLICATION_CONTEXT.getBean(beanName);
    } catch (NoSuchBeanDefinitionException e) {
      return null;
    }
  }

  public static <T> T safeGetBean(Class<T> clazz) {

    try {
      return APPLICATION_CONTEXT.getBean(clazz);
    } catch (NoSuchBeanDefinitionException e) {
      return null;
    }
  }

  public static Object getBean(String beanName) {

    return APPLICATION_CONTEXT.getBean(beanName);
  }

  public static <T> T getBean(Class<T> clazz) {

    return APPLICATION_CONTEXT.getBean(clazz);
  }

  public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {

    return APPLICATION_CONTEXT.getBeansOfType(clazz);
  }

  public static void publishEvent(ApplicationEvent event) {

    APPLICATION_CONTEXT.publishEvent(event);
  }

  public static String getProperty(String key) {

    return APPLICATION_CONTEXT.getEnvironment().getProperty(key);
  }

  public static String getRequiredProperty(String key) {

    return APPLICATION_CONTEXT.getEnvironment().getRequiredProperty(key);
  }

  public static String resolvePlaceholders(String s) {

    return APPLICATION_CONTEXT.getEnvironment().resolvePlaceholders(s);
  }

  public static void validate(Object obj) {
    Validator validator = getBean(Validator.class);
    Set<ConstraintViolation<Object>> errors = validator.validate(obj);
    if (errors != null && errors.size() > 0) {
      throw new ConstraintViolationException("参数校验失败！", errors);
    }
  }

  public static Environment getEnv() {
    return APPLICATION_CONTEXT.getEnvironment();
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    APPLICATION_CONTEXT = applicationContext;
  }
}
