package com.lframework.starter.web.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Spring Application 工具类
 *
 * @author zmj
 */
@Component
public class ApplicationUtil implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT;

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        APPLICATION_CONTEXT = applicationContext;
    }
}
