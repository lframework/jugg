package com.lframework.starter.web.core.utils;

import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * HTTP请求工具类
 * 提供HTTP请求相关的工具方法，支持请求信息获取和处理
 * 包括请求头获取、参数提取、IP地址获取、请求体读取等功能
 *
 * @author lframework@163.com
 */
@Slf4j
public class RequestUtil {

  /**
   * 获取当前HTTP请求对象
   * 从Spring的RequestContextHolder中获取当前请求
   *
   * @return HTTP请求对象
   */
  public static HttpServletRequest getRequest() {

    HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder
        .currentRequestAttributes())).getRequest();

    return request;
  }

  /**
   * 获取当前HTTP响应对象
   * 从Spring的RequestContextHolder中获取当前响应
   *
   * @return HTTP响应对象
   */
  public static HttpServletResponse getResponse() {

    HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder
        .currentRequestAttributes())).getResponse();

    return response;
  }

  /**
   * 获取所有请求头
   * 获取当前请求的所有请求头信息
   *
   * @return 请求头映射，如果无请求头则返回空Map
   */
  public static Map<String, String> getHeaders() {

    HttpServletRequest request = getRequest();

    Enumeration<String> headerNames = request.getHeaderNames();
    if (CollectionUtil.isEmpty(headerNames)) {
      return CollectionUtil.emptyMap();
    }

    Map<String, String> headers = new HashMap<>();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      headers.put(headerName, request.getHeader(headerName));
    }

    return headers;
  }

  /**
   * 获取请求参数
   * 从指定请求中提取所有参数
   *
   * @param request HTTP请求对象，不能为null
   * @return 参数映射，参数名为键，参数值为值
   */
  public static Map<String, Object> getParameters(HttpServletRequest request) {

    Map<String, String[]> parameterMap = request.getParameterMap();
    Map<String, Object> parameters = new HashMap<>(parameterMap.size());

    parameterMap.entrySet().stream().forEach(entry -> {
      String[] values = entry.getValue();
      if (!ArrayUtil.isEmpty(values)) {
        parameters.put(entry.getKey(), values[0]);
      }
    });

    return parameters;
  }

  /**
   * 获取请求体字节数组
   * 从指定请求中读取请求体内容
   *
   * @param request HTTP请求对象，不能为null
   * @return 请求体字节数组，如果读取失败则返回null
   */
  public static byte[] getRequestBody(HttpServletRequest request) {

    int len = request.getContentLength();
    ServletInputStream is = null;
    try {
      is = request.getInputStream();

      byte[] buffer = new byte[len];
      is.read(buffer, 0, len);

      return buffer;
    } catch (IOException e) {
      log.error(e.getMessage(), e);

      return null;
    }
  }

  /**
   * 获取当前请求体字节数组
   * 从当前请求中读取请求体内容
   *
   * @return 请求体字节数组，如果读取失败则返回null
   */
  public static byte[] getRequestBody() {

    HttpServletRequest request = getRequest();
    return getRequestBody(request);
  }

  /**
   * 获取请求体字符串
   * 从指定请求中读取请求体内容并转换为UTF-8字符串
   *
   * @param request HTTP请求对象，不能为null
   * @return 请求体字符串，如果读取失败则返回null
   */
  public static String getRequestBodyStr(HttpServletRequest request) {
    byte[] bytes = getRequestBody(request);

    return new String(bytes, StandardCharsets.UTF_8);
  }

  /**
   * 获取当前请求体字符串
   * 从当前请求中读取请求体内容并转换为UTF-8字符串
   *
   * @return 请求体字符串，如果读取失败则返回null
   */
  public static String getRequestBodyStr() {
    byte[] bytes = getRequestBody();

    return new String(bytes, StandardCharsets.UTF_8);
  }

  /**
   * 获取当前请求参数
   * 从当前请求中提取所有参数
   *
   * @return 参数映射，参数名为键，参数值为值
   */
  public static Map<String, Object> getParameters() {

    Map<String, String[]> parameterMap = getRequest().getParameterMap();
    Map<String, Object> parameters = new HashMap<>(parameterMap.size());

    parameterMap.entrySet().stream().forEach(entry -> {
      String[] values = entry.getValue();
      if (!ArrayUtil.isEmpty(values)) {
        parameters.put(entry.getKey(), values[0]);
      }
    });

    return parameters;
  }

  /**
   * 获取请求的客户端IP地址
   * 通过多种方式尝试获取真实的客户端IP地址
   *
   * @return 客户端IP地址
   */
  public static String getRequestIp() {

    HttpServletRequest request = getRequest();

    String ip = request.getHeader("x-forwarded-for");
    if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
      // 多次反向代理后会有多个ip值，第一个ip才是真实ip
      if (ip.indexOf(",") != -1) {
        ip = ip.split(",")[0];
      }
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Real-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }
}
